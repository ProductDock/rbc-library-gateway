package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto {

    public String id;
    public String title;
    public String author;
    public String cover;
    public List<BookRecordDto> records;
}
