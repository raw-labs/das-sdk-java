package com.rawlabs.das.sdk.java;

import com.rawlabs.protocol.das.FunctionDefinition;
import com.rawlabs.protocol.das.TableDefinition;

import javax.annotation.Nullable;
import java.util.List;

public interface DASSdk {
  List<TableDefinition> getTableDefinitions();

  List<FunctionDefinition> getFunctionDefinitions();

  @Nullable
  DASTable getTable(String name);

  @Nullable
  DASFunction getFunction(String name);
}
