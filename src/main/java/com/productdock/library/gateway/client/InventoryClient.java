package com.productdock.library.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class InventoryClient {

    @Value("${inventory.service.url}/api/inventory/book/")
    private String inventoryServiceUrl;

    private WebClient webClient;

    public InventoryClient(){
        this.webClient = WebClient.create();
    }

    public Mono<Integer> getAvailableBookCopiesCount(String bookId, String jwtToken){
        var inventoryBookUrl = inventoryServiceUrl + bookId;

        return webClient
                .get()
                .uri(inventoryBookUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(Integer.class)
                .onErrorReturn(RuntimeException.class,0);
    }

}
