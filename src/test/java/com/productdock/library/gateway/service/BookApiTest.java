package com.productdock.library.gateway.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.empty;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8081)
@ActiveProfiles("test")
class BookApiTest{

    private static final String bookId= "1";

    @Autowired
    ApplicationContext context;

    WebTestClient rest;

    @BeforeEach
    public void setup() {
        this.rest = WebTestClient
                .bindToApplicationContext(this.context)
                .apply(springSecurity())
                .configureClient()
                .build();
    }

    @Test
    @WithMockUser
    void givenBookId_thenGetBookDetails() throws Exception {
        stubFor(get(urlEqualTo("/api/catalog/books/" + bookId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\": \"1\", \"title\": \"Title\", \"author\": \"John Doe\", \"cover\": \"Cover\"}")));

        rest.mutateWith(mockJwt()).get().uri("http://localhost:8080/api/books/" + bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.title").isEqualTo("Title")
                .jsonPath("$.author").isEqualTo("John Doe")
                .jsonPath("$.cover").isEqualTo("Cover")
                .jsonPath("$.records").value(empty());
    }
}
