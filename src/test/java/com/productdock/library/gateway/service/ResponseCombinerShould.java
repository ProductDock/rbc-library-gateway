package com.productdock.library.gateway.service;

import com.productdock.library.gateway.book.BookDto;
import com.productdock.library.gateway.book.BookRecordMapper;
import com.productdock.library.gateway.book.RentalRecordsMapper;
import com.productdock.library.gateway.book.ResponseCombiner;
import com.productdock.library.gateway.data.provider.BookDtoMother;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ResponseCombinerShould {

    @InjectMocks
    private ResponseCombiner responseCombiner;

    @Mock
    private RentalRecordsMapper rentalRecordsMapper;

    @Mock
    private BookRecordMapper bookRecordMapper;

}
