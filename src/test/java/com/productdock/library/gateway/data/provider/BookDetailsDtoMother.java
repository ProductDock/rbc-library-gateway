package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookDetailsDto;
import com.productdock.library.gateway.book.BookDto;
import com.productdock.library.gateway.book.BookRentalRecordDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.gateway.data.provider.BookDtoMother.defaultBookDto;
import static com.productdock.library.gateway.data.provider.BookRentalRecordDtoMother.defaultBookRentalRecordDto;

public class BookDetailsDtoMother {

    private static BookDto bookDto = defaultBookDto();
    private static final List<BookRentalRecordDto> defaultRecords = new ArrayList<>
            (Arrays.asList(defaultBookRentalRecordDto()));

    public static BookDetailsDto defaultBookDetailsDto() {
        return new BookDetailsDto(bookDto, defaultRecords);
    }

}
