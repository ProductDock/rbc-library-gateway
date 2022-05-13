package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookRentalRecordDto;
import com.productdock.library.gateway.book.BookStatus;

public class BookRentalRecordDtoMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final BookStatus defaultStatus = BookStatus.RENTED;

    public static BookRentalRecordDto defaultBookRentalRecordDto() {
        return defaultBookRentalRecordDtoBuilder().build();
    }

    public static BookRentalRecordDto.BookRentalRecordDtoBuilder defaultBookRentalRecordDtoBuilder() {
        return BookRentalRecordDto.builder()
                .email(defaultUserEmail)
                .status(defaultStatus);
    }

}
