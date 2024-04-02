package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto {
    private Object bookDataDto;
    private List<Object> rentalRecordsDto;
    private Integer availableBookCount;
    private Boolean bookSubscription;
}
