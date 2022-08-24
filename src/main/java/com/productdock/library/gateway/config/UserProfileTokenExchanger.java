package com.productdock.library.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserProfileTokenExchanger {

    @Value("${user-profiles.service.url}/api/user-profiles/token")
    private String userProfilesJwtEndpoint;

    private final WebClient webClient;

    public UserProfileTokenExchanger() {
        this.webClient = WebClient.create();
    }

    public Mono<String> exchangeForUserProfileToken(String openIdToken) {
        return webClient
                .post()
                .uri(userProfilesJwtEndpoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openIdToken)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("");
    }
}
