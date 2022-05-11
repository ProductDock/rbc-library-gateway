package com.productdock.library.gateway.book;

import com.productdock.library.gateway.client.CatalogServiceClient;
import com.productdock.library.gateway.client.InventoryServiceClient;
import com.productdock.library.gateway.client.RentalServiceClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public record BookService(CatalogServiceClient catalogServiceClient, RentalServiceClient rentalServiceClient,
                          InventoryServiceClient inventoryServiceClient, ResponseCombiner responseCombiner) {

    @SneakyThrows
    public BookDetailsDto getBookDetails(String bookId, String jwtToken) {
        var bookDtoMono = catalogServiceClient.getBookData(bookId, jwtToken);
        var rentalRecordsDtoMono = rentalServiceClient.getBookRentalRecords(bookId, jwtToken);
        var availableBooksCountMono = inventoryServiceClient.getAvailableBookCopiesCount(bookId, jwtToken);

        var bookDetailsDtoMono = Mono.zip(bookDtoMono, rentalRecordsDtoMono, availableBooksCountMono).flatMap(tuple -> {
            var book = responseCombiner.generateBookDetailsDto(tuple.getT1(), tuple.getT2(), tuple.getT3());
            return Mono.just(book);
        });
        return bookDetailsDtoMono.toFuture().get();
    }
}
