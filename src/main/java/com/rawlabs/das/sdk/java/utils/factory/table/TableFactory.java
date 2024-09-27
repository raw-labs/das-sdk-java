package com.rawlabs.das.sdk.java.utils.factory.table;

import com.rawlabs.protocol.das.ColumnDefinition;
import com.rawlabs.protocol.das.TableDefinition;
import com.rawlabs.protocol.das.TableId;

import java.util.List;

public final class TableFactory {
  public static TableDefinition createTable(
      String name, String description, List<ColumnDefinition> columns) {
    TableDefinition.Builder tableBuilder =
        TableDefinition.newBuilder()
            .setTableId(TableId.newBuilder().setName(name).build())
            .setDescription(description);
    columns.forEach(tableBuilder::addColumns);
    return tableBuilder.build();
  }
}
