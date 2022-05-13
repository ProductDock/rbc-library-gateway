package com.productdock.library.gateway.service;

import com.productdock.library.gateway.book.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.productdock.library.gateway.data.provider.BookInteractionMother.defaultBookInteraction;
import static com.productdock.library.gateway.data.provider.BookRentalRecordDtoMother.defaultBookRentalRecordDto;
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
        var rentalRecordsDto = List.of(defaultBookRentalRecordDto());

        var bookRentalRecords = rentalRecordsMapper.toDomain(rentalRecordsDto);

        assertThatRecordsAreMatching(bookRentalRecords, rentalRecordsDto);
    }

    @Test
    void mapRentalRecordToRentalRecordDto() {
        var rentalRecords = List.of(defaultBookInteraction());

        var bookRentalRecordsDto = rentalRecordsMapper.toDto(rentalRecords);

        assertThatRecordsAreMatching(rentalRecords, bookRentalRecordsDto);
    }

    private void assertThatRecordsAreMatching(List<BookInteraction> rentalRecords, List<BookRentalRecordDto> rentalRecordsDto) {
        assertThat(rentalRecords).hasSameSizeAs(rentalRecordsDto);
        var rentalRecord = rentalRecords.get(0);
        var rentalRecordDto = rentalRecordsDto.get(0);
        assertThatBookCopyIsMatching(rentalRecord, rentalRecordDto);
    }

    private void assertThatBookCopyIsMatching(BookInteraction rentalRecord, BookRentalRecordDto recordDto) {
        assertThat(rentalRecord.getEmail()).isEqualTo(recordDto.getEmail());
        assertThat(rentalRecord.getStatus()).isEqualTo(recordDto.getStatus());
    }

}
