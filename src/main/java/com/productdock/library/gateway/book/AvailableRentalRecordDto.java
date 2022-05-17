package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AvailableRentalRecordDto {
    public final String email = "";
    public final String status = "AVAILABLE";
}
