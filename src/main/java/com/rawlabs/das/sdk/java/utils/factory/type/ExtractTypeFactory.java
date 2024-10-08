package com.rawlabs.das.sdk.java.utils.factory.type;

import com.rawlabs.protocol.raw.AttrType;
import com.rawlabs.protocol.raw.Type;
import com.rawlabs.protocol.raw.Value;
import com.rawlabs.protocol.raw.ValueRecordField;

import java.util.List;

import static com.rawlabs.das.sdk.java.utils.factory.type.TypeFactory.*;

public class ExtractTypeFactory {
  public static Type extractType(Value value) {
    return switch (value) {
      case Value v when v.hasNull() -> null;
      case Value v when v.hasString() -> createStringType();
      case Value v when v.hasBool() -> createBoolType();
      case Value v when v.hasByte() -> createByteType();
      case Value v when v.hasShort() -> createShortType();
      case Value v when v.hasInt() -> createIntType();
      case Value v when v.hasLong() -> createLongType();
      case Value v when v.hasFloat() -> createFloatType();
      case Value v when v.hasDouble() -> createDoubleType();
      case Value v when v.hasDecimal() -> createDecimalType();
      case Value v when v.hasBinary() -> createBinaryType();
      case Value v when v.hasDate() -> createDateType();
      case Value v when v.hasTime() -> createTimeType();
      case Value v when v.hasTimestamp() -> createTimestampType();
      case Value v when v.hasInterval() -> createIntervalType();
      case Value v when v.hasRecord() -> {
        List<ValueRecordField> fields = v.getRecord().getFieldsList();
        List<AttrType> atts =
            fields.stream()
                .map(t -> createAttType(t.getName(), extractType(t.getValue())))
                .toList();
        yield createRecordType(atts);
      }
      case Value v when v.hasList() -> {
        if (v.getList().getValuesCount() == 0) {
          yield createListType(createUndefinedType());
        }
        Type innerType = extractType(v.getList().getValues(0));
        yield createListType(innerType);
      }
      default -> throw new IllegalStateException("Unexpected value: " + value);
    };
  }
}
