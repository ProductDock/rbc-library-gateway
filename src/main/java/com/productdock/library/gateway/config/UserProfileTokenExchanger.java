package com.productdock.library.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserProfileTokenExchanger {

    @Value("${user-profiles.service.url}/api/user-profiles/access-token")
    private String accessTokenEndpoint;

    private WebClient webClient;

    public UserProfileTokenExchanger() {
        this.webClient = WebClient.create();
    }

    public Mono<String> exchangeForUserProfileToken(String openIdToken) {
        return webClient
                .post()
                .uri(accessTokenEndpoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openIdToken)
                .retrieve()
                .toBodilessEntity()
                .map(voidResponseEntity -> voidResponseEntity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .onErrorReturn("");
    }
}
