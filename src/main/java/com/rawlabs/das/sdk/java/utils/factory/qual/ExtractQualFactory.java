package com.rawlabs.das.sdk.java.utils.factory.qual;

import com.rawlabs.das.sdk.java.exceptions.DASSdkApiException;
import com.rawlabs.das.sdk.java.utils.factory.value.DefaultExtractValueFactory;
import com.rawlabs.das.sdk.java.utils.factory.value.ExtractValueFactory;
import com.rawlabs.protocol.das.Qual;

import java.util.List;
import java.util.function.Function;

public class ExtractQualFactory {

  protected static ExtractValueFactory extractValueFactory = new DefaultExtractValueFactory();

  private static List<Object> extract(
      List<Qual> quals, String fieldName, Function<Qual, Boolean> predicate) {
    return quals.stream()
        .filter(
            q ->
                q.hasSimpleQual()
                    && (q.getSimpleQual().hasOperator())
                    && predicate.apply(q)
                    && q.getFieldName().equals(fieldName))
        .map(qual -> extractValueFactory.extractValue(qual.getSimpleQual().getValue()))
        .toList();
  }

  private static Object extractDistinct(List<Object> qualsResult, String columnName) {
    if (qualsResult.size() > 1) {
      throw new DASSdkApiException("Only one filter can be specified for %s".formatted(columnName));
    }
    return qualsResult.isEmpty() ? null : qualsResult.getFirst();
  }

  public static List<Object> extractEq(List<Qual> quals, String fieldName) {
    return extract(quals, fieldName, q -> q.getSimpleQual().getOperator().hasEquals());
  }

  public static Object extractEqDistinct(List<Qual> quals, String fieldName) {
    return extractDistinct(extractEq(quals, fieldName), fieldName);
  }

  public static List<Object> extractNeq(List<Qual> quals, String fieldName) {
    return extract(quals, fieldName, q -> q.getSimpleQual().getOperator().hasNotEquals());
  }

  public static Object extractNeqDistinct(List<Qual> quals, String fieldName) {
    return extractDistinct(extractNeq(quals, fieldName), fieldName);
  }

  public static List<Object> extractGt(List<Qual> quals, String fieldName) {
    return extract(quals, fieldName, q -> q.getSimpleQual().getOperator().hasGreaterThan());
  }

  public static Object extractGtDistinct(List<Qual> quals, String fieldName) {
    return extractDistinct(extractGt(quals, fieldName), fieldName);
  }

  public static List<Object> extractGte(List<Qual> quals, String fieldName) {
    return extract(quals, fieldName, q -> q.getSimpleQual().getOperator().hasGreaterThanOrEqual());
  }

  public static Object extractGteDistinct(List<Qual> quals, String fieldName) {
    return extractDistinct(extractGte(quals, fieldName), fieldName);
  }

  public static List<Object> extractLt(List<Qual> quals, String fieldName) {
    return extract(quals, fieldName, q -> q.getSimpleQual().getOperator().hasLessThan());
  }

  public static Object extractLtDistinct(List<Qual> quals, String fieldName) {
    return extractDistinct(extractLt(quals, fieldName), fieldName);
  }

  public static List<Object> extractLte(List<Qual> quals, String fieldName) {
    return extract(quals, fieldName, q -> q.getSimpleQual().getOperator().hasLessThanOrEqual());
  }

  public static Object extractLteDistinct(List<Qual> quals, String fieldName) {
    return extractDistinct(extractLte(quals, fieldName), fieldName);
  }
}
