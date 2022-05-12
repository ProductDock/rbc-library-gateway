package com.productdock.library.gateway.service;

import com.productdock.library.gateway.book.ResponseCombiner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.productdock.library.gateway.data.provider.BookDtoMother.defaultBookDto;
import static com.productdock.library.gateway.data.provider.BookRecordDtoMother.defaultBookRecordDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@ActiveProfiles("test")
class ResponseCombinerTest {

    @Autowired
    private ResponseCombiner responseCombiner;

    @Test
    void generateBookDetailsDtoWithAvailableRecord_whenAvailableBookCopiesExist(){
        var bookDto = defaultBookDto();
        var rentalRecordsDto = List.of(defaultBookRecordDto());
        var availableBookCount = 1;

        var bookDetails = responseCombiner.generateBookDetailsDto(bookDto,rentalRecordsDto,availableBookCount);

        assertThat(bookDetails.id).isEqualTo(bookDto.id);
        assertThat(bookDetails.title).isEqualTo(bookDto.title);
        assertThat(bookDetails.author).isEqualTo(bookDto.author);
        assertThat(bookDetails.cover).isEqualTo(bookDto.cover);
        assertThat(bookDetails.records).hasSize(2);
    }

    @Test
    void generateBookDetailsDto_whenAvailableBookCopiesDoNotExist(){
        var bookDto = defaultBookDto();
        var rentalRecordsDto = List.of(defaultBookRecordDto());
        var availableBookCount = 0;

        var bookDetails = responseCombiner.generateBookDetailsDto(bookDto,rentalRecordsDto,availableBookCount);

        assertThat(bookDetails.id).isEqualTo(bookDto.id);
        assertThat(bookDetails.title).isEqualTo(bookDto.title);
        assertThat(bookDetails.author).isEqualTo(bookDto.author);
        assertThat(bookDetails.cover).isEqualTo(bookDto.cover);
        assertThat(bookDetails.records).isEqualTo(rentalRecordsDto);
    }
}
