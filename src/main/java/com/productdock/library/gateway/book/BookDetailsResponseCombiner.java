package com.productdock.library.gateway.book;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Stream.concat;

@Component
public class BookDetailsResponseCombiner {

    public BookDetailsDto generateBookDetailsDto(BookDto book, List<BookRentalRecordDto> rentalRecords, int availableBooksCount) {
        var availableRecords = generateAvailableRecords(availableBooksCount);
        var allRecords = concat(rentalRecords.stream(), availableRecords.stream()).toList();
        return new BookDetailsDto(book, allRecords);
    }

    private List<BookRentalRecordDto> generateAvailableRecords(int availableBooksCount) {
        var availableRecords = new ArrayList<BookRentalRecordDto>();
        for(int i=0;i<availableBooksCount;i++){
            availableRecords.add(new BookRentalRecordDto("", BookStatus.AVAILABLE));
        }
        return availableRecords;
    }
}
