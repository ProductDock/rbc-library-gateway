package com.productdock.library.gateway.data.provider;

import com.productdock.library.gateway.book.RentalRecords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.gateway.data.provider.BookInteractionMother.defaultBookInteraction;

public class RentalRecordsMother {
    private static final String defaultBookId = "1";

    private static final List<RentalRecords.BookInteraction> defaultRecords = new ArrayList<>
            (Arrays.asList(defaultBookInteraction()));

    public static RentalRecords defaultRentalRecord() {
        return RentalRecords.builder()
                .records(defaultRecords)
                .build();
    }

}
