package com.productdock.library.gateway.service;

import com.productdock.library.gateway.book.BookDetailsResponseCombiner;
import com.productdock.library.gateway.book.BookRentalRecordDto;
import com.productdock.library.gateway.book.BookStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.productdock.library.gateway.data.provider.BookDtoMother.defaultBookDto;
import static com.productdock.library.gateway.data.provider.BookRentalRecordDtoMother.defaultBookRentalRecordDto;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookDetailsResponseCombinerShould {

    @InjectMocks
    private BookDetailsResponseCombiner bookDetailsResponseCombiner;

    @Test
    void generateBookDetailsDtoWithAvailableRecord_whenAvailableBookCopiesExist(){
        var bookDto = defaultBookDto();
        var rentalRecordsDto = List.of(defaultBookRentalRecordDto());
        var availableBookCount = 1;

        var bookDetails = bookDetailsResponseCombiner.generateBookDetailsDto(bookDto,rentalRecordsDto,availableBookCount);

        assertThat(bookDetails.id).isEqualTo(bookDto.id);
        assertThat(bookDetails.title).isEqualTo(bookDto.title);
        assertThat(bookDetails.author).isEqualTo(bookDto.author);
        assertThat(bookDetails.cover).isEqualTo(bookDto.cover);
        assertThat(bookDetails.records).contains(new BookRentalRecordDto("", BookStatus.AVAILABLE));
    }

    @Test
    void generateBookDetailsDto_whenAvailableBookCopiesDoNotExist(){
        var bookDto = defaultBookDto();
        var rentalRecordsDto = List.of(defaultBookRentalRecordDto());
        var availableBookCount = 0;

        var bookDetails = bookDetailsResponseCombiner.generateBookDetailsDto(bookDto,rentalRecordsDto,availableBookCount);

        assertThat(bookDetails.records).isEqualTo(rentalRecordsDto);
    }
}
