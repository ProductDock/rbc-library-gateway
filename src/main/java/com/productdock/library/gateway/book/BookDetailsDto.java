package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto extends BookDto {

    public List<BookRentalRecordDto> records;

    public BookDetailsDto(BookDto bookDto, List<BookRentalRecordDto> records){
        super(bookDto.id, bookDto.title, bookDto.author, bookDto.cover);
        this.records = records;
    }
}
