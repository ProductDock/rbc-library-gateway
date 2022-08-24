package com.productdock.library.gateway.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.time.Duration;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOidcLogin;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "10000")
class BookApiTest {

    private static final String BOOK_ID = "1";
    private static final String TITLE = "Title";
    private static final String AUTHOR = "John Doe";

    @Autowired
    ApplicationContext context;

    @Autowired
    WebTestClient rest;

    public static MockWebServer mockCatalogBackEnd;
    public static MockWebServer mockUserProfilesBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockCatalogBackEnd = new MockWebServer();
        mockUserProfilesBackEnd = new MockWebServer();
        mockCatalogBackEnd.start(8082);
        mockUserProfilesBackEnd.start(8085);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockCatalogBackEnd.shutdown();
        mockUserProfilesBackEnd.shutdown();
    }

    @Test
    @WithMockUser
    void givenBookId_thenGetBookDetails() {
        mockUserProfilesBackEnd.enqueue(new MockResponse().setBody("id-token"));
        mockCatalogBackEnd.enqueue(new MockResponse()
                .setBody("{\"id\": \"1\", \"title\": \"Title\", \"author\": \"John Doe\", \"cover\": \"Cover\", " +
                        "\"reviews\":[{\"userFullName\":\"John Doe\",\"rating\":5,\"recommendation\":[\"JUNIOR\"],\"comment\":\"Must read!\"}]}")
                .addHeader("Content-Type", "application/json"));

        rest.mutateWith(mockOidcLogin()).get().uri("/api/books/" + BOOK_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.title").isEqualTo("Title")
                .jsonPath("$.author").isEqualTo("John Doe")
                .jsonPath("$.cover").isEqualTo("Cover")
                .jsonPath("$.reviews[0].userFullName").isEqualTo("John Doe")
                .jsonPath("$.reviews[0].rating").isEqualTo(5)
                .jsonPath("$.reviews[0].recommendation[0]").isEqualTo("JUNIOR")
                .jsonPath("$.reviews[0].recommendation").value(hasSize(1))
                .jsonPath("$.reviews[0].comment").isEqualTo("Must read!")
                .jsonPath("$.records").value(empty());
    }

    @Test
    @WithMockUser
    void givenTitleAndAuthor_thenGetBookDetails() {
        mockCatalogBackEnd.enqueue(new MockResponse()
                .setBody("{\"id\": \"1\", \"title\": \"Title\", \"author\": \"John Doe\", \"cover\": \"Cover\", " +
                        "\"reviews\":[{\"userFullName\":\"John Doe\",\"rating\":5,\"recommendation\":[\"JUNIOR\"],\"comment\":\"Must read!\"}]}")
                .setHeader("Content-Type", "application/json"));

     //   rest.mutateWith(mockJwt()).get().uri("/api/books?title=" + TITLE + "&author=" + AUTHOR)
    }
}
