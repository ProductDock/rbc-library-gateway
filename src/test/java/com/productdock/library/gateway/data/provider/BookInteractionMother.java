package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookStatus;
import com.productdock.library.gateway.book.RentalRecords;

import java.util.Date;

public class BookInteractionMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final BookStatus defaultStatus = BookStatus.RENTED;

    public static RentalRecords.BookInteraction defaultBookInteraction() {
        return defaultBookInteractionBuilder().build();
    }

    public static RentalRecords.BookInteraction.BookInteractionBuilder defaultBookInteractionBuilder() {
        return RentalRecords.BookInteraction.builder()
                .email(defaultUserEmail)
                .status(defaultStatus);
    }

}
