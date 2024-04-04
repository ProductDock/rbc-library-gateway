package com.productdock.library.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

@Component
public class InventoryClient {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    private WebClient webClient;

    public InventoryClient() {
        this.webClient = WebClient.create();
    }

    public Mono<Integer> getAvailableBookCopiesCount(String bookId, String jwtToken) {
        var inventoryBookUrl = inventoryServiceUrl + "/api/inventory/book/" + bookId;

        return webClient
                .get()
                .uri(inventoryBookUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(Integer.class)
                .onErrorReturn(RuntimeException.class, 0);
    }

    public Mono<Boolean> getBookSubscription(String bookId, String jwtToken, String userId) {
        var subscriptionUrl = inventoryServiceUrl + "/api/inventory/books/" + bookId + "/subscriptions";
        var uri = new DefaultUriBuilderFactory(subscriptionUrl)
                .builder()
                .queryParam("userId", userId)
                .build();


        return webClient
                .get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .toBodilessEntity()
                .map(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false);
    }

}
