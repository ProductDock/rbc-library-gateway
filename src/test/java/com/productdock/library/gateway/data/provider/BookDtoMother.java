package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookDto;
import com.productdock.library.gateway.book.BookStatus;
import com.productdock.library.gateway.book.RentalRecords;

public class BookDtoMother {
    private static final String defaultBookId = "1";
    private static final String defaultAuthor = "John Doe";
    private static final String defaultTitle = "Title";
    private static final String defaultCover = "Cover";


    public static BookDto defaultBookDto() {
        return defaultBookDtoBuilder().build();
    }

    public static BookDto.BookDtoBuilder defaultBookDtoBuilder() {
        return BookDto.builder()
                .id(defaultBookId)
                .author(defaultAuthor)
                .title(defaultTitle)
                .cover(defaultCover);
    }
}
