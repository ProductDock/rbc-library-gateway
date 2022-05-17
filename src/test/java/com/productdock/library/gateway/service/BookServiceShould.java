package com.productdock.library.gateway.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.productdock.library.gateway.book.*;
import com.productdock.library.gateway.client.CatalogClient;
import com.productdock.library.gateway.client.InventoryClient;
import com.productdock.library.gateway.client.RentalClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BookServiceShould {

    private static final String BOOK_ID = "1";
    private static final String JWT_TOKEN = "";
    private static final int AVAILABLE_BOOK_COUNT = 1;
    public static final Object CATALOG_RESPONSE = Mockito.mock(Object.class);
    public static final List<Object> RENTAL_RESPONSE = List.of(mock(Object.class));
    private static final Mono<Object> BOOK_DTO_MONO = Mono.just(CATALOG_RESPONSE);
    private static final Mono<List<Object>> RENTAL_RECORDS_DTO_MONO = Mono.just(RENTAL_RESPONSE);
    private static final Mono<Integer> AVAILABLE_BOOK_COUNT_MONO = Mono.just(AVAILABLE_BOOK_COUNT);
    private static final JsonNode BOOK_DETAILS_DTO_JSON = Mockito.mock(JsonNode.class);

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
    void generateBookDetailsDto() {
        given(catalogClient.getBookData(BOOK_ID, JWT_TOKEN)).willReturn(BOOK_DTO_MONO);
        given(rentalClient.getBookRentalRecords(BOOK_ID, JWT_TOKEN)).willReturn(RENTAL_RECORDS_DTO_MONO);
        given(inventoryClient.getAvailableBookCopiesCount(BOOK_ID, JWT_TOKEN)).willReturn(AVAILABLE_BOOK_COUNT_MONO);
        given(bookDetailsResponseCombiner.generateBookDetailsDto(CATALOG_RESPONSE, RENTAL_RESPONSE, AVAILABLE_BOOK_COUNT)).willReturn(BOOK_DETAILS_DTO_JSON);

        var bookDetails = bookService.getBookDetails(BOOK_ID, JWT_TOKEN);

        assertThat(bookDetails).isEqualTo(BOOK_DETAILS_DTO_JSON);
    }

}
