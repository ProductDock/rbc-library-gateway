package com.productdock.library.gateway.book;

import java.util.List;

public record BookDetailsDto(Object bookData, List<Object> rentalRecords, Integer availableBookCount,
                             Boolean bookSubscription) {
}
