package com.productdock.library.gateway.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTParser;
import com.productdock.library.gateway.client.CatalogClient;
import com.productdock.library.gateway.client.InventoryClient;
import com.productdock.library.gateway.client.RentalClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.text.ParseException;
import java.util.List;

@Service
public record BookService(CatalogClient catalogClient, RentalClient rentalClient,
                          InventoryClient inventoryClient, BookDetailsResponseCombiner bookDetailsResponseCombiner) {

    private static final String CLAIM_EMAIL = "email";

    @SneakyThrows
    public JsonNode getBookDetailsById(String bookId, String jwtToken) {
        var userId = extractUserId(jwtToken);

        var bookDtoMono = catalogClient.getBookData(bookId, jwtToken);
        var rentalRecordsDtoMono = rentalClient.getBookRentalRecords(bookId, jwtToken);
        var availableBooksCountMono = inventoryClient.getAvailableBookCopiesCount(bookId, jwtToken);
        var bookSubscriptionMono = inventoryClient.isUserSubscribedToBook(bookId, jwtToken, userId);

        var bookDetailsDtoMono = generateBookDetailsDtoMono(Mono.zip(bookDtoMono, rentalRecordsDtoMono, availableBooksCountMono, bookSubscriptionMono));

        return bookDetailsDtoMono.toFuture().get();
    }

    @SneakyThrows
    public JsonNode getBookDetailsByTitleAndAuthor(String title, String author, String jwtToken) {
        var userId = extractUserId(jwtToken);

        var bookDtoMono = catalogClient.getBookDataByTitleAndAuthor(title, author, jwtToken);
        var bookDetails = bookDtoMono.toFuture().get();
        String bookId = getIdFromBook(bookDetails);
        var rentalRecordsDtoMono = rentalClient.getBookRentalRecords(bookId, jwtToken);
        var availableBooksCountMono = inventoryClient.getAvailableBookCopiesCount(bookId, jwtToken);
        var bookSubscriptionMono = inventoryClient.isUserSubscribedToBook(bookId, jwtToken, userId);

        var bookDetailsDtoMono = generateBookDetailsDtoMono(Mono.zip(Mono.just(bookDetails), rentalRecordsDtoMono, availableBooksCountMono, bookSubscriptionMono));

        return bookDetailsDtoMono.toFuture().get();
    }

    private Mono<JsonNode> generateBookDetailsDtoMono(Mono<Tuple4<Object, List<Object>, Integer, Boolean>> mono) {
        return mono.flatMap(tuple -> {
            var bookDetailsDto = new BookDetailsDto(tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4());
            var book = bookDetailsResponseCombiner.generateBookDetailsDto(bookDetailsDto);
            return Mono.just(book);
        });
    }

    private String getIdFromBook(Object bookDetails) {
        var bookNode = jsonOf(bookDetails);
        var bookIdNode = bookNode.get("id");
        return bookIdNode.asText();
    }

    private String extractUserId(String jwtToken) throws ParseException {
        var jwt = JWTParser.parse(jwtToken);
        return jwt.getJWTClaimsSet().getClaim(CLAIM_EMAIL).toString();
    }

    private JsonNode jsonOf(Object book) {
        return new ObjectMapper().valueToTree(book);
    }
}
