package com.productdock.library.gateway.service;

import com.productdock.library.gateway.book.*;
import com.productdock.library.gateway.client.CatalogServiceClient;
import com.productdock.library.gateway.client.InventoryServiceClient;
import com.productdock.library.gateway.client.RentalServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.gateway.data.provider.BookDetailsDtoMother.defaultBookDetailsDto;
import static com.productdock.library.gateway.data.provider.BookDtoMother.defaultBookDto;
import static com.productdock.library.gateway.data.provider.BookInteractionMother.defaultBookInteraction;
import static com.productdock.library.gateway.data.provider.BookRecordDtoMother.defaultBookRecordDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceShould {

    private static final String BOOK_ID = "1";
    private static final String JWT_TOKEN = "";
    private static final int AVAILABLE_BOOK_COUNT = 0;
    private static final Mono<BookDto> BOOK_DTO_MONO = Mono.just(defaultBookDto());
    private static final Mono<List<BookRecordDto>> RENTAL_RECORDS_DTO_MONO = Mono.just(Arrays.asList(defaultBookRecordDto()));
    private static final Mono<Integer> AVAILABLE_BOOK_COUNT_MONO = Mono.just(AVAILABLE_BOOK_COUNT);
    private static final BookDetailsDto BOOK_DETAILS_DTO_MONO = defaultBookDetailsDto();

    @InjectMocks
    private BookService bookService;

    @Mock
    private CatalogServiceClient catalogServiceClient;

    @Mock
    private RentalServiceClient rentalServiceClient;

    @Mock
    private InventoryServiceClient inventoryServiceClient;

    @Mock
    private ResponseCombiner responseCombiner;

    @Test
    void generateBookDetailsDto() {
        given(catalogServiceClient.getBookData(BOOK_ID, JWT_TOKEN)).willReturn(BOOK_DTO_MONO);
        given(rentalServiceClient.getBookRentalRecords(BOOK_ID, JWT_TOKEN)).willReturn(RENTAL_RECORDS_DTO_MONO);
        given(inventoryServiceClient.getAvailableBookCopiesCount(BOOK_ID, JWT_TOKEN)).willReturn(AVAILABLE_BOOK_COUNT_MONO);
        given(responseCombiner.generateBookDetailsDto(defaultBookDto(), Arrays.asList(defaultBookRecordDto()), AVAILABLE_BOOK_COUNT)).willReturn(BOOK_DETAILS_DTO_MONO);

        var bookDetails = bookService.getBookDetails(BOOK_ID, JWT_TOKEN);

        assertThat(bookDetails).isEqualTo(BOOK_DETAILS_DTO_MONO);
    }

}
