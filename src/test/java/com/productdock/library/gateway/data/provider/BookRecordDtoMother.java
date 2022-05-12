package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookRecordDto;
import com.productdock.library.gateway.book.BookStatus;

public class BookRecordDtoMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final BookStatus defaultStatus = BookStatus.RENTED;

    public static BookRecordDto defaultBookRecordDto() {
        return defaultBookRecordDtoBuilder().build();
    }

    public static BookRecordDto.BookRecordDtoBuilder defaultBookRecordDtoBuilder() {
        return BookRecordDto.builder()
                .email(defaultUserEmail)
                .status(defaultStatus);
    }

}
