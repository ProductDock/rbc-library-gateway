package com.productdock.library.gateway.service;

import com.productdock.library.gateway.book.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static com.productdock.library.gateway.data.provider.BookInteractionMother.defaultBookInteraction;
import static com.productdock.library.gateway.data.provider.BookRecordDtoMother.defaultBookRecordDto;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RentalRecordsMapperImpl.class, BookRecordMapperImpl.class})
class RentalRecordsMapperShould {

    @Autowired
    private RentalRecordsMapper rentalRecordsMapper;

    @Autowired
    private BookRecordMapper bookRecordMapper;

    @Test
    void mapRentalRecordDtoToRentalRecord() {
        var rentalRecordsDto = Arrays.asList(defaultBookRecordDto());

        var bookRentalRecords = rentalRecordsMapper.toDomain(rentalRecordsDto);

        assertThatRecordsAreMatching(bookRentalRecords, rentalRecordsDto);
    }

    @Test
    void mapRentalRecordToRentalRecordDto() {
        var rentalRecords = Arrays.asList(defaultBookInteraction());

        var bookRentalRecordsDto = rentalRecordsMapper.toDto(rentalRecords);

        assertThatRecordsAreMatching(rentalRecords, bookRentalRecordsDto);
    }

    private void assertThatRecordsAreMatching(List<BookInteraction> rentalRecords, List<BookRecordDto> rentalRecordsDto) {
        assertThat(rentalRecords).hasSameSizeAs(rentalRecordsDto);
        var rentalRecord = rentalRecords.get(0);
        var rentalRecordDto = rentalRecordsDto.get(0);
        assertThatBookCopyIsMatching(rentalRecord, rentalRecordDto);
    }

    private void assertThatBookCopyIsMatching(BookInteraction rentalRecord, BookRecordDto recordDto) {
        assertThat(rentalRecord.getEmail()).isEqualTo(recordDto.getEmail());
        assertThat(rentalRecord.getStatus()).isEqualTo(recordDto.getStatus());
    }

}
