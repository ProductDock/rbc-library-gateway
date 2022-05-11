package com.productdock.library.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class InventoryServiceClient {

    @Value("${inventory.service.url}/api/inventory/books/")
    private String inventoryServiceUrl;

    public Mono<Integer> getAvailableBookCopiesCount(String bookId, String jwtToken){
        var inventoryBookUrl = inventoryServiceUrl + bookId;
        return WebClient.create()
                .get()
                .uri(inventoryBookUrl)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(Integer.class)
                .onErrorReturn(RuntimeException.class,0);
    }

}
