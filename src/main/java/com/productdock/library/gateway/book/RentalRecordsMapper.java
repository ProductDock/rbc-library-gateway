package com.productdock.library.gateway.book;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {BookRecordMapper.class})
public interface RentalRecordsMapper {

    @Mapping(target = "records", source = "source.records")
    List<BookInteraction> toDomain(List<BookRentalRecordDto> source);

    @Mapping(target = "records", source = "source.records")
    List<BookRentalRecordDto> toDto(List<BookInteraction> source);

}
