package com.productdock.library.gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.security.test.context.support.WithMockUser;
import wiremock.org.apache.http.HttpResponse;
import wiremock.org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClientBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8083)
class RentalRouteTest {

    @Test
    @WithMockUser
    void givenAuthenticated_thenRequestSentToRentalService() throws Exception {
        stubFor(get(urlEqualTo("/api/rental/books"))
                .willReturn(aResponse()
                        .withStatus(200)));

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:8080/api/rental/books");
        request.addHeader("Authorization", "Token");
        HttpResponse response = client.execute(request);

        verify(1, getRequestedFor(urlEqualTo("/api/rental/books")).withHeader("Authorization", equalTo("Token")));
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }

}
