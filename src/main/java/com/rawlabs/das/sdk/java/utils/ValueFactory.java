package com.rawlabs.das.sdk.java.utils;

import com.google.protobuf.ByteString;
import com.rawlabs.das.sdk.java.exceptions.DASSdkException;
import com.rawlabs.das.sdk.java.exceptions.DASSdkUnsupportedException;
import com.rawlabs.protocol.raw.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// TODO (AZ) test this
public class ValueFactory {
  @SuppressWarnings("unchecked")
  public Value createValue(Object obj, Type type) {
    return switch (type) {
      case Type t when t.hasString() -> this.createString((String) obj);
      case Type t when t.hasBool() -> this.createBool((boolean) obj);
      case Type t when t.hasByte() -> this.createByte((byte) obj);
      case Type t when t.hasShort() -> this.createShort((short) obj);
      case Type t when t.hasInt() -> this.createInt((int) obj);
      case Type t when t.hasLong() -> this.createLong((long) obj);
      case Type t when t.hasFloat() -> this.createFloat((float) obj);
      case Type t when t.hasDouble() -> this.createDouble((double) obj);
      case Type t when t.hasDecimal() -> this.createDecimal((String) obj);
      case Type t when t.hasBinary() -> this.createBinary((byte[]) obj);
      case Type t when t.hasAny() -> this.createAny(obj);
      case Type t when t.hasDate() -> this.createDate((LocalDate) obj);
      case Type t when t.hasTime() -> this.createTime((LocalTime) obj);
      case Type t when t.hasTimestamp() -> this.createTimestamp((LocalDateTime) obj);
      case Type t when t.hasInterval() -> this.createInterval(obj);
      case Type t when t.hasIterable() -> this.createIterable(obj);
      case Type t when t.hasOr() -> this.createOr(obj);
      case Type t when t.hasRecord() -> this.createRecord(obj);
      case Type t when t.hasUndefined() -> this.createUndefined(obj);
      case Type t when t.hasList() ->
          this.createList((List<Object>) obj, t.getList().getInnerType());

      default -> throw new IllegalStateException("Unexpected value: " + type);
    };
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

  protected Value createOr(Object obj) {
    throw new DASSdkException("Or type is not implemented yet");
  }

  protected Value createRecord(Object obj) {
    throw new DASSdkException("Record type is not implemented yet");
  }

  protected Value createUndefined(Object obj) {
    throw new DASSdkException("Undefined type is not implemented yet");
  }
}
