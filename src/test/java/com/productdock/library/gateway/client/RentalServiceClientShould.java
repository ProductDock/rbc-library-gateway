package com.productdock.library.gateway.client;

import com.productdock.library.gateway.book.BookRentalRecordDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RentalServiceClientShould {

    @InjectMocks
    private RentalServiceClient rentalServiceClient;

    @Mock
    private WebClient webClientMock;

    //    @Captor
//    private CaptorValue<URI> uriValue;

    @Test
    void setupClientRequestWithGivenParams() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        given(webClientMock.get()).willReturn(uriSpec);

        WebClient.RequestHeadersSpec headerSpec = mock(WebClient.RequestHeadersSpec.class);
        given(uriSpec.uri(contains("12345"))).willReturn(headerSpec);
        given(headerSpec.header("Authorization", "Token")).willReturn(headerSpec);

        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        given(headerSpec.retrieve()).willReturn(responseSpec);

        given(responseSpec.bodyToMono(new ParameterizedTypeReference<List<BookRentalRecordDto>>() {})).willReturn(Mono.empty());

        rentalServiceClient.getBookRentalRecords("12345", "Token");
    }
}
