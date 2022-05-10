package com.productdock.library.gateway.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/books")
public class BookApi {

    @Value("${catalog.service.url}")
    private String catalogServiceUrl;
    @Value("${rental.service.url}")
    private String rentalServiceUrl;
    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;
    @Autowired
    private ResponseCombiner responseCombiner;
    private static final String authorization = "Authorization";

    @GetMapping("/{bookId}")
    public BookDetailsDto getBook(@PathVariable("bookId") String bookId, Authentication authentication) {
        String catalogBookDetailsUrl = catalogServiceUrl + "/api/catalog/books/" + bookId;
        String rentalBookRecordsUrl = rentalServiceUrl + "/api/rental/record/" + bookId;
        String inventoryBookUrl = inventoryServiceUrl + "/api/inventory/books/" + bookId;
        String jwtToken = "Bearer " + ((Jwt) authentication.getCredentials()).getTokenValue();
        Mono<RentalRecordsDto> recordsMono = WebClient.create()
                .get()
                .uri(rentalBookRecordsUrl)
                .header(authorization, jwtToken)
                .retrieve()
                .bodyToMono(RentalRecordsDto.class)
                .onErrorReturn(RuntimeException.class,new RentalRecordsDto());
        Mono<BookDto> bookDtoMono = WebClient.create()
                .get()
                .uri(catalogBookDetailsUrl)
                .header(authorization, jwtToken)
                .retrieve()
                .bodyToMono(BookDto.class)
                .onErrorReturn(RuntimeException.class,new BookDto());
        Mono<Integer> availableBooksCountMono = WebClient.create()
                .get()
                .uri(inventoryBookUrl)
                .header(authorization, jwtToken)
                .retrieve()
                .bodyToMono(Integer.class)
                .onErrorReturn(RuntimeException.class,0);
        CompletableFuture<RentalRecordsDto> recordsFuture = recordsMono.toFuture();
        CompletableFuture<BookDto> bookFuture = bookDtoMono.toFuture();
        CompletableFuture<Integer> availableBooksCountFuture = availableBooksCountMono.toFuture();
        BookDto book = bookFuture.join();
        RentalRecordsDto rentalRecords = recordsFuture.join();
        int availableBooksCount = availableBooksCountFuture.join();
        return responseCombiner.generateBookDetailsDto(book, rentalRecords, availableBooksCount);
    }
}
