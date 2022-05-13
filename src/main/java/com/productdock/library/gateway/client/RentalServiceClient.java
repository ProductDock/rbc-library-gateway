package com.productdock.library.gateway.client;

import com.productdock.library.gateway.book.BookRentalRecordDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalServiceClient {

    @Value("${rental.service.url}/api/rental/record/")
    private String rentalServiceUrl;

    private WebClient webClient;

    public RentalServiceClient(){
        this.webClient = WebClient.create();
    }

    public Mono<List<BookRentalRecordDto>> getBookRentalRecords(String bookId, String jwtToken){
        var rentalBookRecordsUrl = rentalServiceUrl + bookId;
        return webClient
                .get()
                .uri(rentalBookRecordsUrl)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BookRentalRecordDto>>() {})
                .onErrorReturn(RuntimeException.class,new ArrayList<BookRentalRecordDto>());
    }
}
