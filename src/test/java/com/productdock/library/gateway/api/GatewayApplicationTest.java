package com.productdock.library.gateway.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8081)
public class GatewayApplicationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void givenUnauthenticated_thenUnauthorizedResponse() throws Exception {
        webClient
                .get().uri("/api/books")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser
    public void givenAuthenticated_thenStatusOkResponse() throws Exception {
        stubFor(get(urlEqualTo("/api/books"))
                .willReturn(aResponse()
                        .withStatus(200)));

        webClient
                .get().uri("/api/books")
                .exchange()
                .expectStatus().isOk();
    }

}
