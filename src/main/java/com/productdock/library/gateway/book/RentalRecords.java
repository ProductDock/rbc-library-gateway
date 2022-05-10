package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public class RentalRecords {

    public List<BookInteraction> records;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookInteraction{
        public String email;
        public BookStatus status;
    }
}
