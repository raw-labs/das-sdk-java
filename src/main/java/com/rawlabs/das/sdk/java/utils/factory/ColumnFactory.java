package com.rawlabs.das.sdk.java.utils.factory;

import com.rawlabs.protocol.das.ColumnDefinition;
import com.rawlabs.protocol.raw.Type;

public class ColumnFactory {
  public static ColumnDefinition createColumn(String name, String description, Type type) {
    return ColumnDefinition.newBuilder()
        .setName(name)
        .setDescription(description)
        .setType(type)
        .build();
  }
}
