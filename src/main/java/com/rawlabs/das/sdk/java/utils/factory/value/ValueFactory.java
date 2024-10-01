package com.rawlabs.das.sdk.java.utils.factory.value;

import com.google.protobuf.ByteString;
import com.rawlabs.das.sdk.java.exceptions.DASSdkException;
import com.rawlabs.protocol.raw.*;

import java.time.OffsetDateTime;
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
              () -> this.createInterval(valueTypeTuple.value()));
      case Type t when t.hasIterable() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getIterable().getNullable(),
              () -> this.createIterable(valueTypeTuple.value()));
      case Type t when t.hasOr() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getOr().getNullable(),
              () -> this.createOr(valueTypeTuple.value()));
      case Type t when t.hasRecord() ->
          withNullCheck(
              valueTypeTuple.value(),
              t.getRecord().getNullable(),
              () -> this.createRecord(valueTypeTuple.value()));
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

  protected Value createAny(Object obj) {
    throw new DASSdkException("Any type is not implemented yet");
  }

  protected Value createDate(String date) {
    OffsetDateTime dateTime = OffsetDateTime.parse(date);
    return Value.newBuilder()
        .setDate(
            ValueDate.newBuilder()
                .setDay(dateTime.getDayOfMonth())
                .setMonth(dateTime.getMonthValue())
                .setYear(dateTime.getYear())
                .build())
        .build();
  }

  protected Value createTime(String time) {
    OffsetDateTime dateTime = OffsetDateTime.parse(time);
    return Value.newBuilder()
        .setTime(
            ValueTime.newBuilder()
                .setHour(dateTime.getHour())
                .setMinute(dateTime.getMinute())
                .setSecond(dateTime.getSecond())
                .setNano(dateTime.getNano())
                .build())
        .build();
  }

  protected Value createTimestamp(String timestamp) {
    OffsetDateTime dateTime = OffsetDateTime.parse(timestamp);
    return Value.newBuilder()
        .setTimestamp(
            ValueTimestamp.newBuilder()
                .setDay(dateTime.getDayOfMonth())
                .setMonth(dateTime.getMonthValue())
                .setYear(dateTime.getYear())
                .setHour(dateTime.getHour())
                .setMinute(dateTime.getMinute())
                .setSecond(dateTime.getSecond())
                .setNano(dateTime.getNano())
                .build())
        .build();
  }

  protected Value createInterval(Object interval) {
    throw new DASSdkException("Interval type is not implemented yet");
  }

  protected Value createIterable(Object iterable) {
    throw new DASSdkException("Iterable type is not implemented yet");
  }

  protected Value createOr(Object or) {
    throw new DASSdkException("Or type is not implemented yet");
  }

  @SuppressWarnings("unchecked")
  protected Value createRecord(Object value) {
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

  protected Value createUndefined() {
    throw new DASSdkException("Undefined type is not implemented yet");
  }
}
