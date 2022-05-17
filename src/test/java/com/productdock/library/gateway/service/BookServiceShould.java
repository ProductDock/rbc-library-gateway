//package com.productdock.library.gateway.service;
//
//import com.productdock.library.gateway.book.*;
//import com.productdock.library.gateway.client.CatalogClient;
//import com.productdock.library.gateway.client.InventoryClient;
//import com.productdock.library.gateway.client.RentalClient;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static com.productdock.library.gateway.data.provider.BookDetailsDtoMother.defaultBookDetailsDto;
//import static com.productdock.library.gateway.data.provider.BookDtoMother.defaultBookDto;
//import static com.productdock.library.gateway.data.provider.BookRentalRecordDtoMother.defaultBookRentalRecordDto;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class BookServiceShould {
//
//    private static final String BOOK_ID = "1";
//    private static final String JWT_TOKEN = "";
//    private static final int AVAILABLE_BOOK_COUNT = 1;
//    private static final Mono<String> BOOK_DTO_MONO = Mono.just(null);
//    private static final Mono<List<BookRentalRecordDto>> RENTAL_RECORDS_DTO_MONO = Mono.just(Arrays.asList(defaultBookRentalRecordDto()));
//    private static final Mono<Integer> AVAILABLE_BOOK_COUNT_MONO = Mono.just(AVAILABLE_BOOK_COUNT);
//    //private static final BookDetailsDto BOOK_DETAILS_DTO_MONO = defaultBookDetailsDto();
//
//    @InjectMocks
//    private BookService bookService;
//
//    @Mock
//    private CatalogClient catalogClient;
//
//    @Mock
//    private RentalClient rentalClient;
//
//    @Mock
//    private InventoryClient inventoryClient;
//
//    @Mock
//    private BookDetailsResponseCombiner bookDetailsResponseCombiner;
//
//    @Test
//    void generateBookDetailsDto() {
//        given(catalogClient.getBookData(BOOK_ID, JWT_TOKEN)).willReturn(BOOK_DTO_MONO);
//        given(rentalClient.getBookRentalRecords(BOOK_ID, JWT_TOKEN)).willReturn(RENTAL_RECORDS_DTO_MONO);
//        given(inventoryClient.getAvailableBookCopiesCount(BOOK_ID, JWT_TOKEN)).willReturn(AVAILABLE_BOOK_COUNT_MONO);
//        given(bookDetailsResponseCombiner.generateBookDetailsDto(defaultBookDto(), List.of(defaultBookRentalRecordDto()), AVAILABLE_BOOK_COUNT)).willReturn(BOOK_DETAILS_DTO_MONO);
//
//        var bookDetails = bookService.getBookDetails(BOOK_ID, JWT_TOKEN);
//
//        assertThat(bookDetails).isEqualTo(BOOK_DETAILS_DTO_MONO);
//    }
//
//}
