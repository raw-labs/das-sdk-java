package com.rawlabs.das.sdk.java.utils.factory;

import com.rawlabs.protocol.das.*;
import com.rawlabs.protocol.raw.Value;

public class QualFactory {
  public static Qual createEq(Value v, String fieldName) {
    return Qual.newBuilder()
        .setSimpleQual(
            SimpleQual.newBuilder()
                .setValue(v)
                .setOperator(Operator.newBuilder().setEquals(Equals.newBuilder().build())))
        .setFieldName(fieldName)
        .build();
  }

  public static Qual createNeq(Value v, String fieldName) {
    return Qual.newBuilder()
        .setSimpleQual(
            SimpleQual.newBuilder()
                .setValue(v)
                .setOperator(Operator.newBuilder().setNotEquals(NotEquals.newBuilder().build())))
        .setFieldName(fieldName)
        .build();
  }

  public static Qual createGt(Value v, String fieldName) {
    return Qual.newBuilder()
        .setSimpleQual(
            SimpleQual.newBuilder()
                .setValue(v)
                .setOperator(
                    Operator.newBuilder().setGreaterThan(GreaterThan.newBuilder().build())))
        .setFieldName(fieldName)
        .build();
  }

  public static Qual createGte(Value v, String fieldName) {
    return Qual.newBuilder()
        .setSimpleQual(
            SimpleQual.newBuilder()
                .setValue(v)
                .setOperator(
                    Operator.newBuilder()
                        .setGreaterThanOrEqual(GreaterThanOrEqual.newBuilder().build())))
        .setFieldName(fieldName)
        .build();
  }

  public static Qual createLt(Value v, String fieldName) {
    return Qual.newBuilder()
        .setSimpleQual(
            SimpleQual.newBuilder()
                .setValue(v)
                .setOperator(Operator.newBuilder().setLessThan(LessThan.newBuilder().build())))
        .setFieldName(fieldName)
        .build();
  }

  public static Qual createLte(Value v, String fieldName) {
    return Qual.newBuilder()
        .setSimpleQual(
            SimpleQual.newBuilder()
                .setValue(v)
                .setOperator(
                    Operator.newBuilder().setLessThanOrEqual(LessThanOrEqual.newBuilder().build())))
        .setFieldName(fieldName)
        .build();
  }
}
