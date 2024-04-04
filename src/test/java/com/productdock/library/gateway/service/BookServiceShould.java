package com.productdock.library.gateway.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.gateway.book.BookDetailsResponseCombiner;
import com.productdock.library.gateway.book.BookService;
import com.productdock.library.gateway.client.CatalogClient;
import com.productdock.library.gateway.client.InventoryClient;
import com.productdock.library.gateway.client.RentalClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BookServiceShould {

    private static final String BOOK_ID = "1";
    private static final String BOOK_TITLE = "::title::";
    private static final String BOOK_AUTHOR = "::author::";
    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVtYWlsIn0.9_82fPfiHdHoOkyx8WQY8FsgPivtguil3QL5a3bDE7g";
    private static final String USER_ID = "email";
    private static final int AVAILABLE_BOOK_COUNT = 1;
    private static final boolean BOOK_SUBSCRIPTION = false;
    private static final Object CATALOG_RESPONSE = Mockito.mock(Object.class);
    private static final List<Object> RENTAL_RESPONSE = List.of(mock(Object.class));
    private static final Mono<Object> CATALOG_MONO = Mono.just(CATALOG_RESPONSE);
    private static final Mono<List<Object>> RENTAL_MONO = Mono.just(RENTAL_RESPONSE);
    private static final Mono<Integer> AVAILABLE_BOOK_COUNT_MONO = Mono.just(AVAILABLE_BOOK_COUNT);
    private static final Mono<Boolean> BOOK_SUBSCRIPTION_MONO = Mono.just(BOOK_SUBSCRIPTION);
    private static final JsonNode BOOK_DETAILS_JSON = Mockito.mock(JsonNode.class);

    @InjectMocks
    private BookService bookService;

    @Mock
    private CatalogClient catalogClient;

    @Mock
    private RentalClient rentalClient;

    @Mock
    private InventoryClient inventoryClient;

    @Mock
    private BookDetailsResponseCombiner bookDetailsResponseCombiner;

    @Test
    void generateBookDetailsDtoByBookId() {
        given(catalogClient.getBookData(BOOK_ID, JWT_TOKEN)).willReturn(CATALOG_MONO);
        given(rentalClient.getBookRentalRecords(BOOK_ID, JWT_TOKEN)).willReturn(RENTAL_MONO);
        given(inventoryClient.getAvailableBookCopiesCount(BOOK_ID, JWT_TOKEN)).willReturn(AVAILABLE_BOOK_COUNT_MONO);
        given(inventoryClient.getBookSubscription(BOOK_ID, JWT_TOKEN, USER_ID)).willReturn(BOOK_SUBSCRIPTION_MONO);
        given(bookDetailsResponseCombiner.generateBookDetailsDto(any())).willReturn(BOOK_DETAILS_JSON);

        var bookDetails = bookService.getBookDetailsById(BOOK_ID, JWT_TOKEN);

        assertThat(bookDetails).isEqualTo(BOOK_DETAILS_JSON);
    }

    @Test
    @SneakyThrows
    void generateBookDetailsDtoByBookTitleAndAuthor() {
        Object catalogResponse = new ObjectMapper()
                .readValue("{\"id\": \"1\", \"title\": \"::title::\", \"author\":\"::author::\"}", Object.class);
        var catalogMono = Mono.just(catalogResponse);
        given(catalogClient.getBookDataByTitleAndAuthor(BOOK_TITLE, BOOK_AUTHOR, JWT_TOKEN)).willReturn(catalogMono);
        given(rentalClient.getBookRentalRecords(BOOK_ID, JWT_TOKEN)).willReturn(RENTAL_MONO);
        given(inventoryClient.getAvailableBookCopiesCount(BOOK_ID, JWT_TOKEN)).willReturn(AVAILABLE_BOOK_COUNT_MONO);
        given(inventoryClient.getBookSubscription(BOOK_ID, JWT_TOKEN, USER_ID)).willReturn(BOOK_SUBSCRIPTION_MONO);
        given(bookDetailsResponseCombiner.generateBookDetailsDto(any()))
                .willReturn(BOOK_DETAILS_JSON);

        var bookDetails = bookService.getBookDetailsByTitleAndAuthor(BOOK_TITLE, BOOK_AUTHOR, JWT_TOKEN);

        assertThat(bookDetails).isEqualTo(BOOK_DETAILS_JSON);
    }

}
