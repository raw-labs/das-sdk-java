package com.rawlabs.das.sdk.java;

import com.rawlabs.protocol.das.FunctionDefinition;
import com.rawlabs.protocol.das.TableDefinition;

import java.util.List;

public interface DASSdk {
  List<TableDefinition> getTableDefinitions();

  List<FunctionDefinition> getFunctionDefinitions();

  DASTable getTable(String name);

  DASFunction getFunction(String name);
}
