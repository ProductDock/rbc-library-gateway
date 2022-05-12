package com.productdock.library.gateway.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseCombiner{

    @Autowired
    private RentalRecordsMapper rentalRecordsMapper;

    public BookDetailsDto generateBookDetailsDto(BookDto book, List<BookRecordDto> rentalRecords, int availableBooksCount) {
        var allRecords = addAvailableRecords(rentalRecords, availableBooksCount);
        return new BookDetailsDto(book.id, book.author, book.title, book.cover, allRecords);
    }

    private List<BookRecordDto> addAvailableRecords(List<BookRecordDto> rentalRecordsDto, int availableBooksCount) {
        var rentalRecords = rentalRecordsMapper.toDomain(rentalRecordsDto);
        var i = 0;
        while(i < availableBooksCount){
            rentalRecords.add(new BookInteraction("", BookStatus.AVAILABLE));
            i++;
        }
        return rentalRecordsMapper.toDto(rentalRecords);
    }
}
