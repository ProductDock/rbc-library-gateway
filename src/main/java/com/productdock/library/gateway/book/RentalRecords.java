package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRecords {

    private List<BookInteraction> records;

    public void add(BookInteraction bookInteraction){
        this.records.add(bookInteraction);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class BookInteraction{
        private String email;
        private BookStatus status;
    }
}
