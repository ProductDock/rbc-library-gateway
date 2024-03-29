package com.productdock.library.gateway.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.gateway.client.CatalogClient;
import com.productdock.library.gateway.client.InventoryClient;
import com.productdock.library.gateway.client.RentalClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public record BookService(CatalogClient catalogClient, RentalClient rentalClient,
                          InventoryClient inventoryClient, BookDetailsResponseCombiner bookDetailsResponseCombiner) {

    @SneakyThrows
    public JsonNode getBookDetailsById(String bookId, String jwtToken) {
        var bookDtoMono = catalogClient.getBookData(bookId, jwtToken);
        var rentalRecordsDtoMono = rentalClient.getBookRentalRecords(bookId, jwtToken);
        var availableBooksCountMono = inventoryClient.getAvailableBookCopiesCount(bookId, jwtToken);

        var bookDetailsDtoMono = Mono.zip(bookDtoMono, rentalRecordsDtoMono, availableBooksCountMono).flatMap(tuple -> {
            var book = bookDetailsResponseCombiner.generateBookDetailsDto(tuple.getT1(), tuple.getT2(), tuple.getT3());
            return Mono.just(book);
        });
        return bookDetailsDtoMono.toFuture().get();
    }

    @SneakyThrows
    public JsonNode getBookDetailsByTitleAndAuthor(String title, String author, String jwtToken) {
        var bookDtoMono = catalogClient.getBookDataByTitleAndAuthor(title, author, jwtToken);
        var bookDetails = bookDtoMono.toFuture().get();
        String bookId = getIdFromBook(bookDetails);
        var rentalRecordsDtoMono = rentalClient.getBookRentalRecords(bookId, jwtToken);
        var availableBooksCountMono = inventoryClient.getAvailableBookCopiesCount(bookId, jwtToken);

        var bookDetailsDtoMono = Mono.zip(rentalRecordsDtoMono, availableBooksCountMono).flatMap(tuple -> {
            var book = bookDetailsResponseCombiner.generateBookDetailsDto(bookDetails, tuple.getT1(), tuple.getT2());
            return Mono.just(book);
        });
        return bookDetailsDtoMono.toFuture().get();
    }

    private String getIdFromBook(Object bookDetails) {
        var bookNode = jsonOf(bookDetails);
        var bookIdNode = bookNode.get("id");
        return bookIdNode.asText();
    }

    private JsonNode jsonOf(Object book) {
        return new ObjectMapper().valueToTree(book);
    }
}
