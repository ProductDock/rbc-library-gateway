package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RentalRecordsDto {
    public List<BookRecordDto> records = new ArrayList<>();
}
