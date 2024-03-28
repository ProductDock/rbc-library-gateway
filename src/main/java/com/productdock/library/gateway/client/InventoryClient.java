package com.productdock.library.gateway.client;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class InventoryClient {

    private static final String inventoryServiceUrl = "${inventory.service.url}/api/inventory/book/";
    private static final String subscriptionServiceUrl = "${inventory.service.url}/api/inventory/subscriptions/";

    private WebClient webClient;

    public InventoryClient() {
        this.webClient = WebClient.create();
    }

    public Mono<Integer> getAvailableBookCopiesCount(String bookId, String jwtToken) {
        var inventoryBookUrl = inventoryServiceUrl + bookId;

        return webClient
                .get()
                .uri(inventoryBookUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(Integer.class)
                .onErrorReturn(RuntimeException.class, 0);
    }

    public Mono<Boolean> getBookSubscription(String bookId, String jwtToken) {
        var subscriptionUrl = subscriptionServiceUrl + bookId;

        return webClient
                .get()
                .uri(subscriptionUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorReturn(RuntimeException.class, false);
    }

}
