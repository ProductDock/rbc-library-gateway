package com.productdock.library.gateway.service;

import com.productdock.library.gateway.book.BookDetailsResponseCombiner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BookDetailsResponseCombinerShould {

    @InjectMocks
    private BookDetailsResponseCombiner bookDetailsResponseCombiner;

    @Test
    void generateBookDetailsDtoWithAvailableRecord_whenAvailableBookCopiesExist() {
        var anyDto = new LinkedHashMap<>();
        anyDto.put("property", "value");
        var anyRecord = new LinkedHashMap<>();
        anyRecord.put("recordProperty", "recordValue");
        List<Object> rentalRecordsDto = List.of(anyRecord);
        var availableBookCount = 1;

        var bookDetails = bookDetailsResponseCombiner.generateBookDetailsDto(anyDto, rentalRecordsDto, availableBookCount);

        assertThat(bookDetails.get("property").asText()).isEqualTo("value");
        assertThat(bookDetails.get("records")).isNotNull();
        assertThat(bookDetails.get("records").size()).isEqualTo(2);
        assertThat(bookDetails.get("records").get(0).get("recordProperty").asText()).isEqualTo("recordValue");
        assertThat(bookDetails.get("records").get(1).get("email").asText()).isEmpty();
        assertThat(bookDetails.get("records").get(1).get("status").asText()).isEqualTo("AVAILABLE");
    }

    @Test
    void generateBookDetailsDto_whenAvailableBookCopiesDoNotExist() {
        var anyDto = new LinkedHashMap<>();
        var anyRecord = new LinkedHashMap<>();
        anyRecord.put("recordProperty", "recordValue");
        List<Object> rentalRecordsDto = List.of(anyRecord);
        var availableBookCount = 0;

        var bookDetails = bookDetailsResponseCombiner.generateBookDetailsDto(anyDto, rentalRecordsDto, availableBookCount);

        assertThat(bookDetails.get("records")).isNotNull();
        assertThat(bookDetails.get("records").size()).isEqualTo(1);
    }
}
