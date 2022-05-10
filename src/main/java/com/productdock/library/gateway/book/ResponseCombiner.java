package com.productdock.library.gateway.book;

import org.springframework.stereotype.Component;

@Component
public record ResponseCombiner(RentalRecordsMapper rentalRecordsMapper) {

    public BookDetailsDto generateBookDetailsDto(BookDto book, RentalRecordsDto rentalRecords, int availableBooksCount) {
        var allRecords = addAvailableRecords(rentalRecords, availableBooksCount);
        return new BookDetailsDto(book.id, book.author, book.title, book.cover, allRecords.records);
    }

    private RentalRecordsDto addAvailableRecords(RentalRecordsDto rentalRecordsDto, int availableBooksCount) {
        var rentalRecords = rentalRecordsMapper.toDomain(rentalRecordsDto);
        var i = 0;
        while(i < availableBooksCount){
            rentalRecords.records.add(new RentalRecords.BookInteraction("", BookStatus.AVAILABLE));
            i++;
        }
        return rentalRecordsMapper.toDto(rentalRecords);
    }
}
