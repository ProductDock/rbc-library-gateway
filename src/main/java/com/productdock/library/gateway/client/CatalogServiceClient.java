package com.productdock.library.gateway.client;

import com.productdock.library.gateway.book.BookDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CatalogServiceClient {

    @Value("${catalog.service.url}/api/catalog/books/")
    private String catalogServiceUrl;

    public Mono<BookDto> getBookData(String bookId, String jwtToken){
        var catalogBookDetailsUrl = catalogServiceUrl + bookId;
        return WebClient.create()
                .get()
                .uri(catalogBookDetailsUrl)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(BookDto.class)
                .onErrorReturn(RuntimeException.class,new BookDto());
    }

}