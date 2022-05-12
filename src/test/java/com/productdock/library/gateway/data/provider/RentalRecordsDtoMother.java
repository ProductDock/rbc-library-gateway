package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.BookRecordDto;
import com.productdock.library.gateway.book.RentalRecordsDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.gateway.data.provider.BookRecordDtoMother.defaultBookRecordDto;

public class RentalRecordsDtoMother {

    private static final List<BookRecordDto> defaultRecords = new ArrayList<>
            (Arrays.asList(defaultBookRecordDto()));

    public static RentalRecordsDto defaultRentalRecordsDto() {
        return RentalRecordsDto.builder()
                .records(defaultRecords)
                .build();
    }

}
