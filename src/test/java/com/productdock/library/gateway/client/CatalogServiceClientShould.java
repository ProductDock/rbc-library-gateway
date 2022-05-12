package com.productdock.library.gateway.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CatalogServiceClientShould {

    @InjectMocks
    private CatalogServiceClient catalogServiceClient;

    @Mock
    private WebClient webClientMock;

    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        given(webClientMock.get()).willReturn(uriSpec);

        WebClient.RequestHeadersSpec headerSpec = mock(WebClient.RequestHeadersSpec.class);
        given(uriSpec.uri(contains("12345"))).willReturn(headerSpec);
        given(headerSpec.header("Authorization", "Token")).willReturn(headerSpec);

        catalogServiceClient.getBookData("12345", "Token");
    }
}
