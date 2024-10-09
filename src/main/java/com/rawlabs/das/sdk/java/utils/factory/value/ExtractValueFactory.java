package com.rawlabs.das.sdk.java.utils.factory.value;

import com.rawlabs.protocol.das.Row;
import com.rawlabs.protocol.raw.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class ExtractValueFactory {

  public final Object extractValue(Value value) {
    return switch (value) {
      case Value v when v.hasNull() -> null;
      case Value v when v.hasString() -> v.getString().getV();
      case Value v when v.hasBool() -> v.getBool().getV();
      case Value v when v.hasByte() -> v.getByte().getV();
      case Value v when v.hasShort() -> v.getShort().getV();
      case Value v when v.hasInt() -> v.getInt().getV();
      case Value v when v.hasLong() -> v.getLong().getV();
      case Value v when v.hasFloat() -> v.getFloat().getV();
      case Value v when v.hasDouble() -> v.getDouble().getV();
      case Value v when v.hasDecimal() -> v.getDecimal().getV();
      case Value v when v.hasBinary() -> v.getBinary().getV().toByteArray();
      case Value v when v.hasDate() -> getDate(v);
      case Value v when v.hasTime() -> getTime(v);
      case Value v when v.hasTimestamp() -> getTimestamp(v);
      case Value v when v.hasInterval() -> getInterval(v);
      case Value v when v.hasRecord() -> getRecord(v);
      case Value v when v.hasList() -> getList(v);
      default -> throw new IllegalStateException("Unexpected value: " + value);
    };
  }

  public final Object extractValue(Row row, String columnName) {
    return extractValue(row.getDataMap().get(columnName));
  }

  protected Object getList(Value v) {
    List<Value> list = v.getList().getValuesList();
    List<Object> result = new ArrayList<>(list.size());
    for (Value value : list) {
      result.add(extractValue(value));
    }
    return result;
  }

  protected Object getDate(Value v) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  protected Object getTime(Value v) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  protected Object getTimestamp(Value v) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  protected Object getInterval(Value v) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  protected Object getRecord(Value v) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
