package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookDetailsDto;
import com.productdock.library.gateway.book.BookDto;
import com.productdock.library.gateway.book.BookRecordDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.gateway.data.provider.BookDtoMother.defaultBookDto;
import static com.productdock.library.gateway.data.provider.BookRecordDtoMother.defaultBookRecordDto;

public class BookDetailsDtoMother {

    private static BookDto bookDto = defaultBookDto();
    private static final List<BookRecordDto> defaultRecords = new ArrayList<>
            (Arrays.asList(defaultBookRecordDto()));

    public static BookDetailsDto defaultBookDetailsDto() {
        return defaultBookDetailsDtoBuilder().build();
    }

    public static BookDetailsDto.BookDetailsDtoBuilder defaultBookDetailsDtoBuilder() {
        return BookDetailsDto.builder()
                .id(bookDto.id)
                .author(bookDto.author)
                .title(bookDto.title)
                .cover(bookDto.cover)
                .records(defaultRecords);
    }
}
