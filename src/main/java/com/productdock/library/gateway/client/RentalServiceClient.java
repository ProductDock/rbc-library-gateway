package com.productdock.library.gateway.client;

import com.productdock.library.gateway.book.RentalRecordsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RentalServiceClient {

    @Value("${rental.service.url}/api/rental/record/")
    private String rentalServiceUrl;

    public Mono<RentalRecordsDto> getBookRentalRecords(String bookId, String jwtToken){
        var rentalBookRecordsUrl = rentalServiceUrl + bookId;
        return WebClient.create()
                .get()
                .uri(rentalBookRecordsUrl)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(RentalRecordsDto.class)
                .onErrorReturn(RuntimeException.class,new RentalRecordsDto());
    }
}
