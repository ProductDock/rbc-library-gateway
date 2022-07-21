package com.productdock.library.gateway.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CatalogClient {

    @Value("${catalog.service.url}/api/catalog/books/")
    private String catalogServiceUrl;

    private WebClient webClient;

    public CatalogClient(){
        this.webClient = WebClient.create();
    }

    public Mono<Object> getBookDataById(String bookId, String jwtToken){
        var catalogBookDetailsUrl = catalogServiceUrl + bookId;
        return webClient
                .get()
                .uri(catalogBookDetailsUrl)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorReturn(RuntimeException.class, JsonNodeFactory.instance.objectNode());
    }

    public Mono<Object> getBookDataByTitleAndAuthor(String title, String author, String jwtToken){
        var catalogBookDetailsUrl = catalogServiceUrl + "?title=" + title + "&author=" + author;
        return webClient
                .get()
                .uri(catalogBookDetailsUrl)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorReturn(RuntimeException.class, JsonNodeFactory.instance.objectNode());
    }

}
