package com.productdock.library.gateway.service;

import com.productdock.library.gateway.book.AvailableRentalRecordDto;
import com.productdock.library.gateway.book.BookDetailsResponseCombiner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookDetailsResponseCombinerShould {

    @InjectMocks
    private BookDetailsResponseCombiner bookDetailsResponseCombiner;

    @Test
    void generateBookDetailsDtoWithAvailableRecord_whenAvailableBookCopiesExist(){
        var bookDto = new AvailableRentalRecordDto();
        List<Object> rentalRecordsDto = List.of(new AvailableRentalRecordDto(), new AvailableRentalRecordDto());
        var availableBookCount = 2;

        var bookDetails = bookDetailsResponseCombiner.generateBookDetailsDto(bookDto,rentalRecordsDto,availableBookCount);

        //assertThat(bookDetails.records).contains(new BookRentalRecordDto("", BookStatus.AVAILABLE));
    }

//    @Test
//    void generateBookDetailsDto_whenAvailableBookCopiesDoNotExist(){
//        var bookDto = defaultBookDto();
//        var rentalRecordsDto = List.of(defaultBookRentalRecordDto());
//        var availableBookCount = 0;
//
//        var bookDetails = bookDetailsResponseCombiner.generateBookDetailsDto(bookDto,rentalRecordsDto,availableBookCount);
//
//        assertThat(bookDetails.records).isEqualTo(rentalRecordsDto);
//    }
}
