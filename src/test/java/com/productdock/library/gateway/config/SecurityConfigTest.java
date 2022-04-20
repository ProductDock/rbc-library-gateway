package com.productdock.library.gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void givenUnauthenticated_thenUnauthorizedResponse() throws Exception {
        webClient
                .get().uri("/")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
