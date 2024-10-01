package com.rawlabs.das.sdk.java.utils.factory.type;

import com.rawlabs.protocol.raw.*;

import java.util.List;

public final class TypeFactory {
  public static Type createStringType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setString(StringType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createStringType() {
    return createStringType(false, true);
  }

  public static Type createBoolType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setBool(BoolType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createBoolType() {
    return createBoolType(false, true);
  }

  public static Type createByteType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setByte(ByteType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createByteType() {
    return createByteType(false, true);
  }

  public static Type createShortType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setShort(ShortType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createShortType() {
    return createShortType(false, true);
  }

  public static Type createIntType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setInt(IntType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createIntType() {
    return createIntType(false, true);
  }

  public static Type createLongType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setLong(LongType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createLongType() {
    return createLongType(false, true);
  }

  public static Type createFloatType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setFloat(FloatType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createFloatType() {
    return createFloatType(false, true);
  }

  public static Type createDoubleType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setDouble(DoubleType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createDoubleType() {
    return createDoubleType(false, true);
  }

  public static Type createDecimalType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setDecimal(DecimalType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createDecimalType() {
    return createDecimalType(false, true);
  }

  public static Type createBinaryType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setBinary(BinaryType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createBinaryType() {
    return createBinaryType(false, true);
  }

  public static Type createListType(Type innerType, boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setList(
            ListType.newBuilder()
                .setTriable(triable)
                .setInnerType(innerType)
                .setNullable(nullable)
                .build())
        .build();
  }

  public static Type createListType(Type innerType) {
    return createListType(innerType, false, true);
  }

  public static Type createDateType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setDate(DateType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createDateType() {
    return createDateType(false, true);
  }

  public static Type createTimeType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setTime(TimeType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createTimeType() {
    return createTimeType(false, true);
  }

  public static Type createTimestampType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setTimestamp(TimestampType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createTimestampType() {
    return createTimestampType(false, true);
  }

  public static Type createIntervalType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setInterval(IntervalType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createIntervalType() {
    return createIntervalType(false, true);
  }

  public static Type createIterableType(Type innerType, boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setIterable(IterableType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createIterableType(Type innerType) {
    return createIterableType(innerType, false, true);
  }

  public static Type createOrType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setOr(OrType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createOrType() {
    return createOrType(false, true);
  }

  public static Type createRecordType(boolean triable, boolean nullable, List<AttrType> atts) {
    var recordTypeBuilder = RecordType.newBuilder().setTriable(triable).setNullable(nullable);
    atts.forEach(recordTypeBuilder::addAtts);
    return Type.newBuilder().setRecord(recordTypeBuilder.build()).build();
  }

  public static Type createRecordType(List<AttrType> atts) {
    return createRecordType(false, true, atts);
  }

  public static AttrType createAttType(String name, Type type) {
    return AttrType.newBuilder().setIdn(name).setTipe(type).build();
  }

  public static Type createUndefinedType(boolean triable, boolean nullable) {
    return Type.newBuilder()
        .setUndefined(UndefinedType.newBuilder().setTriable(triable).setNullable(nullable).build())
        .build();
  }

  public static Type createUndefinedType() {
    return createUndefinedType(false, true);
  }

  public static Type createAnyType() {
    return Type.newBuilder().setAny(AnyType.newBuilder().build()).build();
  }
}
