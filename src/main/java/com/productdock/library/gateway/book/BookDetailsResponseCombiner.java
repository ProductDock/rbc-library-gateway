package com.productdock.library.gateway.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Stream.concat;

@Component
public class BookDetailsResponseCombiner {

    private static final String JSON_FIELD_RECORDS = "records";

    public JsonNode generateBookDetailsDto(Object book, List<Object> rentalRecords, int availableBooksCount) {
        List<Object> records = combineRentalRecordsWithAvailable(rentalRecords, availableBooksCount);
        var json = jsonOf(book);
        extendJsonWithRecords((ObjectNode) json, jsonOf(records));
        return json;
    }

    private JsonNode jsonOf(Object book) {
        return new ObjectMapper().valueToTree(book);
    }

    private List<Object> combineRentalRecordsWithAvailable(List<Object> rentalRecords, int availableBooksCount) {
        var availableRecords = generateAvailableRecords(availableBooksCount);
        return concat(rentalRecords.stream(), availableRecords.stream()).toList();
    }

    private List<AvailableRentalRecordDto> generateAvailableRecords(int availableBooksCount) {
        return IntStream.range(0, availableBooksCount).mapToObj(i -> new AvailableRentalRecordDto()).toList();
    }

    private void extendJsonWithRecords(ObjectNode json, JsonNode records) {
        json.putIfAbsent(JSON_FIELD_RECORDS, records);
    }
}
