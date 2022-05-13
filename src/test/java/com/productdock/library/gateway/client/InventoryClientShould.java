package com.productdock.library.gateway.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class InventoryClientShould {

    @InjectMocks
    private InventoryClient inventoryClient;

    @Mock
    private WebClient webClientMock;

    @Test
    void setupClientRequestWithGivenParams() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        given(webClientMock.get()).willReturn(uriSpec);

        WebClient.RequestHeadersSpec headerSpec = mock(WebClient.RequestHeadersSpec.class);
        given(uriSpec.uri(contains("12345"))).willReturn(headerSpec);
        given(headerSpec.header("Authorization", "Token")).willReturn(headerSpec);

        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        given(headerSpec.retrieve()).willReturn(responseSpec);

        given(responseSpec.bodyToMono(Integer.class)).willReturn(Mono.empty());

        var availableBooksCountMono = inventoryClient.getAvailableBookCopiesCount("12345", "Token");

        StepVerifier.create(availableBooksCountMono).verifyComplete();
    }
}
