package com.rawlabs.das.sdk.java.utils;

import com.google.common.collect.BiMap;
import com.rawlabs.protocol.das.ColumnDefinition;
import com.rawlabs.protocol.das.Row;
import com.rawlabs.protocol.das.TableDefinition;
import com.rawlabs.protocol.raw.Value;

import java.util.List;

public abstract class DASRowModelMapper {

  protected final BiMap<String, String> transformations;
  protected final TableDefinition tableDefinition;

  public DASRowModelMapper(BiMap<String, String> transformations, TableDefinition tableDefinition) {
    this.transformations = transformations;
    this.tableDefinition = tableDefinition;
  }

  public Row toRow(Object model) {
    Row.Builder rowBuilder = Row.newBuilder();
    List<ColumnDefinition> columns = tableDefinition.getColumnsList();
    for (ColumnDefinition column : columns) {
      String columnName = column.getName();
      rowBuilder.putData(withTransformation(columnName), getValue(column, model));
    }
    return rowBuilder.build();
  }

  protected String withTransformation(String fieldName) {
    return transformations.getOrDefault(fieldName, fieldName);
  }

  protected String withTransformationInverse(String fieldName) {
    return transformations.inverse().getOrDefault(fieldName, fieldName);
  }

  public abstract Object toModel(Row row);

  protected abstract Value getValue(ColumnDefinition column, Object obj);
}
