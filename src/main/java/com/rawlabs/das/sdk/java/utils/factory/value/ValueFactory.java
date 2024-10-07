package com.rawlabs.das.sdk.java.utils.factory.value;

import com.google.protobuf.ByteString;
import com.rawlabs.das.sdk.java.exceptions.DASSdkException;
import com.rawlabs.protocol.raw.*;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

// TODO (AZ) test this
public abstract class ValueFactory {

  @SuppressWarnings("unchecked")
  public final Value createValue(ValueTypeTuple valueTypeTuple) {
    return switch (valueTypeTuple.type()) {
      case Type t when t.hasString() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getString().getNullable(),
              () -> this.createString((String) valueTypeTuple.value()));
      case Type t when t.hasBool() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getBool().getNullable(),
              () -> this.createBool((boolean) valueTypeTuple.value()));
      case Type t when t.hasByte() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getByte().getNullable(),
              () -> this.createByte((byte) valueTypeTuple.value()));
      case Type t when t.hasShort() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getShort().getNullable(),
              () -> this.createShort((short) valueTypeTuple.value()));
      case Type t when t.hasInt() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getInt().getNullable(),
              () -> this.createInt((int) valueTypeTuple.value()));
      case Type t when t.hasLong() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getLong().getNullable(),
              () -> this.createLong((long) valueTypeTuple.value()));
      case Type t when t.hasFloat() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getFloat().getNullable(),
              () -> this.createFloat((float) valueTypeTuple.value()));
      case Type t when t.hasDouble() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getDouble().getNullable(),
              () -> this.createDouble((double) valueTypeTuple.value()));
      case Type t when t.hasDecimal() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getDecimal().getNullable(),
              () -> this.createDecimal((String) valueTypeTuple.value()));
      case Type t when t.hasBinary() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getBinary().getNullable(),
              () -> this.createBinary((byte[]) valueTypeTuple.value()));
      case Type t when t.hasAny() ->
          withNullCheck(valueTypeTuple.value(), true, () -> this.createAny(valueTypeTuple.value()));
      case Type t when t.hasDate() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getDate().getNullable(),
              () -> this.createDate((String) valueTypeTuple.value()));
      case Type t when t.hasTime() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getTime().getNullable(),
              () -> this.createTime((String) valueTypeTuple.value()));
      case Type t when t.hasTimestamp() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getTimestamp().getNullable(),
              () -> this.createTimestamp((String) valueTypeTuple.value()));
      case Type t when t.hasInterval() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getInterval().getNullable(),
              () -> this.createInterval((String) valueTypeTuple.value()));
      case Type t when t.hasOr() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getOr().getNullable(),
              () -> this.createOr(valueTypeTuple.value()));
      case Type t when t.hasRecord() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getRecord().getNullable(),
              () -> this.createRecordWithType(valueTypeTuple.value()));
      case Type t when t.hasUndefined() ->
          withNullCheck(
              valueTypeTuple.value(), t.getUndefined().getNullable(), this::createUndefined);
      case Type t when t.hasList() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getList().getNullable(),
              () ->
                  this.createList(
                      (List<Object>) valueTypeTuple.value(), t.getList().getInnerType()));

      default -> throw new IllegalStateException("Unexpected value: " + valueTypeTuple.type());
    };
  }

  private boolean isDecimal(String value) {
    try {
      new BigDecimal(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
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

  private boolean isInterval(String value) {
    try {
      Interval.parse(value);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean isRecord(Object value) {
    return value instanceof Map;
  }

  private boolean isList(Object value) {
    return value instanceof List;
  }

  public final Value createValue(Object object) {
    return switch (object) {
      case null -> Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
      case String t when isDecimal(t) -> createDecimal(t);
      case String t when isDate(t) -> createDate(t);
      case String t when isTimestamp(t) -> createTimestamp(t);
      case String t when isTime(t) -> createTime(t);
      case String t when isInterval(t) -> createInterval(t);
      case String t -> createString(t);
      case Boolean t -> createBool(t);
      case Byte t -> createByte(t);
      case Short t -> createShort(t);
      case Integer t -> createInt(t);
      case Long t -> createLong(t);
      case Float t -> createFloat(t);
      case Double t -> createDouble(t);
      case byte[] t -> createBinary(t);
      case Object t when isRecord(t) -> createRecordWithoutType(t);
      case Object t when isList(t) -> createList(t);

      default -> throw new IllegalStateException("Unexpected value: " + object);
    };
  }

  private Value withNullCheck(Object obj, boolean isNullable, Supplier<Value> supplier) {
    if (obj == null && !isNullable) {
      throw new IllegalArgumentException("non nullable value is null");
    }
    if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    }
    return supplier.get();
  }

  protected Value createString(String string) {
    return Value.newBuilder().setString(ValueString.newBuilder().setV(string).build()).build();
  }

  protected Value createBool(boolean bool) {
    return Value.newBuilder().setBool(ValueBool.newBuilder().setV(bool).build()).build();
  }

  protected Value createByte(byte byteValue) {
    return Value.newBuilder().setByte(ValueByte.newBuilder().setV(byteValue).build()).build();
  }

  protected Value createShort(short shortValue) {
    return Value.newBuilder().setShort(ValueShort.newBuilder().setV(shortValue).build()).build();
  }

  protected Value createInt(int intValue) {
    return Value.newBuilder().setInt(ValueInt.newBuilder().setV(intValue).build()).build();
  }

  protected Value createLong(long longValue) {
    return Value.newBuilder().setLong(ValueLong.newBuilder().setV(longValue).build()).build();
  }

  protected Value createFloat(float floatValue) {
    return Value.newBuilder().setFloat(ValueFloat.newBuilder().setV(floatValue).build()).build();
  }

  protected Value createDouble(double doubleValue) {
    return Value.newBuilder().setDouble(ValueDouble.newBuilder().setV(doubleValue).build()).build();
  }

  protected Value createDecimal(String decimalValue) {
    return Value.newBuilder()
        .setDecimal(ValueDecimal.newBuilder().setV(decimalValue).build())
        .build();
  }

  protected Value createBinary(byte[] binaryValue) {
    return Value.newBuilder()
        .setBinary(ValueBinary.newBuilder().setV(ByteString.copyFrom(binaryValue)).build())
        .build();
  }

  protected Value createList(List<Object> list, Type type) {
    List<Value> listOfValues =
        list.stream().map(o -> createValue(new ValueTypeTuple(o, type))).toList();
    return Value.newBuilder().setList(ValueList.newBuilder().addAllValues(listOfValues)).build();
  }

  @SuppressWarnings("unchecked")
  protected Value createList(Object obj) {
    List<Object> list = (List<Object>) obj;
    List<Value> listOfValues = list.stream().map(this::createValue).toList();
    return Value.newBuilder().setList(ValueList.newBuilder().addAllValues(listOfValues)).build();
  }

  protected Value createAny(Object obj) {
    return createValue(obj);
  }

  protected Value createDate(String date) {
    DateTime dateTime = DateTime.parse(date);
    return Value.newBuilder()
        .setDate(
            ValueDate.newBuilder()
                .setDay(dateTime.getDayOfMonth())
                .setMonth(dateTime.getMonthOfYear())
                .setYear(dateTime.getYear())
                .build())
        .build();
  }

  protected Value createTime(String time) {
    DateTime dateTime = DateTime.parse(time);
    return Value.newBuilder()
        .setTime(
            ValueTime.newBuilder()
                .setHour(dateTime.getHourOfDay())
                .setMinute(dateTime.getMinuteOfHour())
                .setSecond(dateTime.getSecondOfDay())
                .setNano(dateTime.getMillisOfSecond() * 1_000_000)
                .build())
        .build();
  }

  protected Value createTimestamp(String timestamp) {
    DateTime dateTime = DateTime.parse(timestamp);
    return Value.newBuilder()
        .setTimestamp(
            ValueTimestamp.newBuilder()
                .setDay(dateTime.getDayOfMonth())
                .setMonth(dateTime.getMonthOfYear())
                .setYear(dateTime.getYear())
                .setHour(dateTime.getHourOfDay())
                .setMinute(dateTime.getMinuteOfHour())
                .setSecond(dateTime.getSecondOfMinute())
                .setNano(dateTime.getMillisOfSecond() * 1_000_000)
                .build())
        .build();
  }

  protected Value createInterval(String interval) {
    Interval i = Interval.parse(interval);
    return Value.newBuilder()
        .setInterval(
            ValueInterval.newBuilder()
                .setDays((int) i.toDuration().getStandardDays())
                .setHours((int) i.toDuration().getStandardHours())
                .setMinutes((int) i.toDuration().getStandardMinutes())
                .setSeconds((int) i.toDuration().getStandardSeconds())
                .setMillis((int) i.toDuration().getMillis())
                .build())
        .build();
  }

  protected Value createOr(Object or) {
    throw new DASSdkException("Or type is not implemented yet");
  }

  @SuppressWarnings("unchecked")
  protected Value createRecordWithType(Object value) {
    Map<String, ValueTypeTuple> recordMap = (Map<String, ValueTypeTuple>) value;
    ValueRecord.Builder rb = ValueRecord.newBuilder();
    List<String> fieldNames = new ArrayList<>(recordMap.keySet());
    for (int i = 0; i < recordMap.size(); i++) {
      rb.addFields(
          ValueRecordField.newBuilder()
              .setName(fieldNames.get(i))
              .setValue(createValue(recordMap.get(fieldNames.get(i)))));
    }
    return Value.newBuilder().setRecord(rb.build()).build();
  }

  @SuppressWarnings("unchecked")
  protected Value createRecordWithoutType(Object obj) {
    Map<String, Object> recordMap = (Map<String, Object>) obj;
    ValueRecord.Builder rb = ValueRecord.newBuilder();
    List<String> fieldNames = new ArrayList<>(recordMap.keySet());
    for (int i = 0; i < recordMap.size(); i++) {
      rb.addFields(
          ValueRecordField.newBuilder()
              .setName(fieldNames.get(i))
              .setValue(createValue(recordMap.get(fieldNames.get(i)))));
    }
    return Value.newBuilder().setRecord(rb.build()).build();
  }

  protected Value createUndefined() {
    throw new DASSdkException("Undefined type is not implemented yet");
  }
}
