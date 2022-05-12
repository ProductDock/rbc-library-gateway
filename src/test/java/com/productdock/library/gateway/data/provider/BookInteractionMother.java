package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookInteraction;
import com.productdock.library.gateway.book.BookStatus;

import java.util.Date;

public class BookInteractionMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final BookStatus defaultStatus = BookStatus.RENTED;

    public static BookInteraction defaultBookInteraction() {
        return defaultBookInteractionBuilder().build();
    }

    public static BookInteraction.BookInteractionBuilder defaultBookInteractionBuilder() {
        return BookInteraction.builder()
                .email(defaultUserEmail)
                .status(defaultStatus);
    }

}
