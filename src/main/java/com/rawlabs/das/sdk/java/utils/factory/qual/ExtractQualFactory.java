package com.rawlabs.das.sdk.java.utils.factory.qual;

import com.rawlabs.das.sdk.java.utils.factory.value.DefaultExtractValueFactory;
import com.rawlabs.das.sdk.java.utils.factory.value.ExtractValueFactory;
import com.rawlabs.protocol.das.Qual;

import java.util.List;
import java.util.Optional;

public class ExtractQualFactory {

  protected static ExtractValueFactory extractValueFactory = new DefaultExtractValueFactory();

  public static Object extractEq(List<Qual> quals, String fieldName) {
    Optional<Qual> simpleQuals =
        quals.stream()
            .filter(
                q ->
                    q.hasSimpleQual()
                        && (q.getSimpleQual().hasOperator())
                        && q.getSimpleQual().getOperator().hasEquals()
                        && q.getFieldName().equals(fieldName))
            .findFirst();
    return simpleQuals
        .map(qual -> extractValueFactory.extractValue(qual.getSimpleQual().getValue()))
        .orElse(null);
  }

  public static Object extractNeq(List<Qual> quals, String fieldName) {
    Optional<Qual> simpleQuals =
        quals.stream()
            .filter(
                q ->
                    q.hasSimpleQual()
                        && (q.getSimpleQual().hasOperator())
                        && q.getSimpleQual().getOperator().hasNotEquals()
                        && q.getFieldName().equals(fieldName))
            .findFirst();
    return simpleQuals
        .map(qual -> extractValueFactory.extractValue(qual.getSimpleQual().getValue()))
        .orElse(null);
  }

  public static Object extractGt(List<Qual> quals, String fieldName) {
    Optional<Qual> simpleQuals =
        quals.stream()
            .filter(
                q ->
                    q.hasSimpleQual()
                        && (q.getSimpleQual().hasOperator())
                        && q.getSimpleQual().getOperator().hasGreaterThan()
                        && q.getFieldName().equals(fieldName))
            .findFirst();
    return simpleQuals
        .map(qual -> extractValueFactory.extractValue(qual.getSimpleQual().getValue()))
        .orElse(null);
  }

  public static Object extractGte(List<Qual> quals, String fieldName) {
    Optional<Qual> simpleQuals =
        quals.stream()
            .filter(
                q ->
                    q.hasSimpleQual()
                        && (q.getSimpleQual().hasOperator())
                        && q.getSimpleQual().getOperator().hasGreaterThanOrEqual()
                        && q.getFieldName().equals(fieldName))
            .findFirst();
    return simpleQuals
        .map(qual -> extractValueFactory.extractValue(qual.getSimpleQual().getValue()))
        .orElse(null);
  }

  public static Object extractLt(List<Qual> quals, String fieldName) {
    Optional<Qual> simpleQuals =
        quals.stream()
            .filter(
                q ->
                    q.hasSimpleQual()
                        && (q.getSimpleQual().hasOperator())
                        && q.getSimpleQual().getOperator().hasLessThan()
                        && q.getFieldName().equals(fieldName))
            .findFirst();
    return simpleQuals
        .map(qual -> extractValueFactory.extractValue(qual.getSimpleQual().getValue()))
        .orElse(null);
  }

  public static Object extractLte(List<Qual> quals, String fieldName) {
    Optional<Qual> simpleQuals =
        quals.stream()
            .filter(
                q ->
                    q.hasSimpleQual()
                        && (q.getSimpleQual().hasOperator())
                        && q.getSimpleQual().getOperator().hasLessThanOrEqual()
                        && q.getFieldName().equals(fieldName))
            .findFirst();
    return simpleQuals
        .map(qual -> extractValueFactory.extractValue(qual.getSimpleQual().getValue()))
        .orElse(null);
  }
}
