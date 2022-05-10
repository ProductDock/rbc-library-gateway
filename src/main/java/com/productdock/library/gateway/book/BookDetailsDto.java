package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BookDetailsDto {

    public String id;
    public String title;
    public String author;
    public String cover;
    public List<BookRecordDto> records;
}
