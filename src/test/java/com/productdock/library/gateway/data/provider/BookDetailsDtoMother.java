package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookDetailsDto;
import com.productdock.library.gateway.book.BookDto;
import com.productdock.library.gateway.book.BookRentalRecordDto;

import java.util.List;

import static com.productdock.library.gateway.data.provider.BookDtoMother.defaultBookDto;
import static com.productdock.library.gateway.data.provider.BookRentalRecordDtoMother.defaultBookRentalRecordDto;

public class BookDetailsDtoMother {

    private static final BookDto bookDto = defaultBookDto();
    private static final List<BookRentalRecordDto> defaultRecords = (List.of(defaultBookRentalRecordDto()));

    public static BookDetailsDto defaultBookDetailsDto() {
        return new BookDetailsDto(bookDto, defaultRecords);
    }

}
