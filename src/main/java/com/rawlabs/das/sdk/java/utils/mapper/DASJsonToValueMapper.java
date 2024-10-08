package com.rawlabs.das.sdk.java.utils.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rawlabs.das.sdk.java.utils.factory.value.DefaultValueFactory;
import com.rawlabs.das.sdk.java.utils.factory.value.ValueFactory;
import com.rawlabs.das.sdk.java.utils.factory.value.ValueTypeTuple;
import com.rawlabs.protocol.raw.Value;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.IOException;

import static com.rawlabs.das.sdk.java.utils.factory.type.TypeFactory.*;

public class DASJsonToValueMapper {

  private final ValueFactory valueFactory = new DefaultValueFactory();

  public Value map(String json) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode node = objectMapper.readTree(json);
    return Value.newBuilder().build();
  }

  private Value recurse(JsonNode jsonNode) throws IOException {
    switch (jsonNode) {
      case JsonNode node when node.isNull() -> valueFactory.createNull();
      case JsonNode node when node.isTextual() -> {
        String value = jsonNode.asText();
        if (isDate(value)) {
          return valueFactory.createValue(new ValueTypeTuple(value, createDateType()));
        } else if (isTime(value)) {
          return valueFactory.createValue(new ValueTypeTuple(value, createDateType()));
        } else if (isTimestamp(value)) {
          return valueFactory.createValue(new ValueTypeTuple(value, createTimestampType()));
        } else {
          return valueFactory.createValue(new ValueTypeTuple(value, createStringType()));
        }
      }
      case JsonNode node when node.isBoolean() ->
          valueFactory.createValue(new ValueTypeTuple(jsonNode.asBoolean(), createBoolType()));
      case JsonNode node when node.isShort() ->
          valueFactory.createValue(new ValueTypeTuple(jsonNode.shortValue(), createShortType()));
      case JsonNode node when node.isInt() ->
          valueFactory.createValue(new ValueTypeTuple(jsonNode.intValue(), createIntType()));
      case JsonNode node when node.isLong() ->
          valueFactory.createValue(new ValueTypeTuple(jsonNode.longValue(), createLongType()));
      case JsonNode node when node.isFloat() ->
          valueFactory.createValue(new ValueTypeTuple(jsonNode.floatValue(), createFloatType()));
      case JsonNode node when node.isDouble() ->
          valueFactory.createValue(new ValueTypeTuple(jsonNode.doubleValue(), createDoubleType()));
      case JsonNode node when node.isObject() -> {}
      case JsonNode node when node.isArray() -> {}
      case JsonNode node when node.isBinary() ->
          valueFactory.createValue(new ValueTypeTuple(jsonNode.binaryValue(), createBinaryType()));
      case JsonNode node when node.isMissingNode() -> valueFactory.createNull();
      case JsonNode node when node.isBigDecimal() ->
          valueFactory.createValue(
              new ValueTypeTuple(jsonNode.decimalValue(), createDecimalType()));
      case JsonNode node when node.isBigInteger() ->
          valueFactory.createValue(
              new ValueTypeTuple(jsonNode.bigIntegerValue(), createDecimalType()));

      default -> throw new IllegalStateException("Unexpected value: " + jsonNode);
    }
    return Value.newBuilder().build();
  }

  private boolean isDate(String value) {
    try {
      LocalDate.parse(value);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean isTime(String value) {
    try {
      LocalTime.parse(value);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean isTimestamp(String value) {
    try {
      DateTime.parse(value);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
