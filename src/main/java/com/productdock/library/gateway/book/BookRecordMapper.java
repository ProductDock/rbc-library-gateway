package com.productdock.library.gateway.book;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookRecordMapper {

    BookRecordDto toDto(RentalRecords.BookInteraction bookInteraction);

    RentalRecords.BookInteraction toDomain(BookRecordDto bookRecordDto);

}
