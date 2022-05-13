package com.productdock.library.gateway.book;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookRecordMapper {

    BookRentalRecordDto toDto(BookInteraction bookInteraction);

    BookInteraction toDomain(BookRentalRecordDto bookRentalRecordDto);

}
