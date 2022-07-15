package com.productdock.library.gateway.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
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

    public Mono<Object> getBookData(String bookId, String jwtToken){
        var catalogBookDetailsUrl = catalogServiceUrl + bookId;
        return webClient
                .get()
                .uri(catalogBookDetailsUrl)
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorReturn(RuntimeException.class, JsonNodeFactory.instance.objectNode());

    }

}
