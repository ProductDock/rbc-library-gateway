package com.productdock.library.gateway.book;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {BookRecordMapper.class})
public interface RentalRecordsMapper {

    @Mapping(target = "records", source = "source.records")
    RentalRecords toDomain(RentalRecordsDto source);

    @Mapping(target = "records", source = "source.records")
    RentalRecordsDto toDto(RentalRecords source);

}
