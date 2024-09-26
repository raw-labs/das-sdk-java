package com.rawlabs.das.sdk.java.utils.factory;

import com.google.protobuf.ByteString;
import com.rawlabs.das.sdk.java.exceptions.DASSdkException;
import com.rawlabs.protocol.raw.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Supplier;

// TODO (AZ) test this
public class ValueFactory {

  private static final ValueFactory instance = new ValueFactory();

  public static ValueFactory getInstance() {
    return instance;
  }

  protected ValueFactory() {}

  @SuppressWarnings("unchecked")
  public Value createValue(Object obj, Type type) {
    return switch (type) {
      case Type t when t.hasString() ->
          withNullCheck(obj, t.getString().getNullable(), () -> this.createString((String) obj));
      case Type t when t.hasBool() ->
          withNullCheck(obj, t.getBool().getNullable(), () -> this.createBool((boolean) obj));
      case Type t when t.hasByte() ->
          withNullCheck(obj, t.getByte().getNullable(), () -> this.createByte((byte) obj));
      case Type t when t.hasShort() ->
          withNullCheck(obj, t.getShort().getNullable(), () -> this.createShort((short) obj));
      case Type t when t.hasInt() ->
          withNullCheck(obj, t.getInt().getNullable(), () -> this.createInt((int) obj));
      case Type t when t.hasLong() ->
          withNullCheck(obj, t.getLong().getNullable(), () -> this.createLong((long) obj));
      case Type t when t.hasFloat() ->
          withNullCheck(obj, t.getFloat().getNullable(), () -> this.createFloat((float) obj));
      case Type t when t.hasDouble() ->
          withNullCheck(obj, t.getDouble().getNullable(), () -> this.createDouble((double) obj));
      case Type t when t.hasDecimal() ->
          withNullCheck(obj, t.getDecimal().getNullable(), () -> this.createDecimal((String) obj));
      case Type t when t.hasBinary() ->
          withNullCheck(obj, t.getBinary().getNullable(), () -> this.createBinary((byte[]) obj));
      case Type t when t.hasAny() -> withNullCheck(obj, true, () -> this.createAny(obj));
      case Type t when t.hasDate() ->
          withNullCheck(obj, t.getDate().getNullable(), () -> this.createDate((LocalDate) obj));
      case Type t when t.hasTime() ->
          withNullCheck(obj, t.getTime().getNullable(), () -> this.createTime((LocalTime) obj));
      case Type t when t.hasTimestamp() ->
          withNullCheck(
              obj, t.getTimestamp().getNullable(), () -> this.createTimestamp((LocalDateTime) obj));
      case Type t when t.hasInterval() ->
          withNullCheck(obj, t.getInterval().getNullable(), () -> this.createInterval(obj));
      case Type t when t.hasIterable() ->
          withNullCheck(obj, t.getIterable().getNullable(), () -> this.createIterable(obj));
      case Type t when t.hasOr() ->
          withNullCheck(obj, t.getOr().getNullable(), () -> this.createOr(obj));
      case Type t when t.hasRecord() ->
          withNullCheck(obj, t.getRecord().getNullable(), () -> this.createRecord(obj));
      case Type t when t.hasUndefined() ->
          withNullCheck(obj, t.getUndefined().getNullable(), this::createUndefined);
      case Type t when t.hasList() ->
          withNullCheck(
              obj,
              t.getList().getNullable(),
              () -> this.createList((List<Object>) obj, t.getList().getInnerType()));

      default -> throw new IllegalStateException("Unexpected value: " + type);
    };
  }

  protected Value withNullCheck(Object obj, boolean isNullable, Supplier<Value> supplier) {
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
    List<Value> listOfValues = list.stream().map(o -> createValue(o, type)).toList();
    return Value.newBuilder().setList(ValueList.newBuilder().addAllValues(listOfValues)).build();
  }

  protected Value createAny(Object obj) {
    throw new DASSdkException("Any type is not implemented yet");
  }

  protected Value createDate(LocalDate date) {
    throw new DASSdkException("Date type is not implemented yet");
  }

  protected Value createTime(LocalTime time) {
    throw new DASSdkException("Time type is not implemented yet");
  }

  protected Value createTimestamp(LocalDateTime timestamp) {
    throw new DASSdkException("Timestamp type is not implemented yet");
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

  protected Value createRecord(Object record) {
    throw new DASSdkException("Record type is not implemented yet");
  }

  protected Value createUndefined() {
    throw new DASSdkException("Undefined type is not implemented yet");
  }
}
