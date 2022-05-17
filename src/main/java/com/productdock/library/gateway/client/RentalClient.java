package com.productdock.library.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalClient {

    @Value("${rental.service.url}/api/rental/record/")
    private String rentalServiceUrl;

    private WebClient webClient;

    public RentalClient(){
        this.webClient = WebClient.create();
    }

    public Mono<List<Object>> getBookRentalRecords(String bookId, String jwtToken){
        var rentalBookRecordsUrl = rentalServiceUrl + bookId;
        return webClient
                .get()
                .uri(rentalBookRecordsUrl)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Object>>() {})
                .onErrorReturn(RuntimeException.class, new ArrayList<>());
    }
}
