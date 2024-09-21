package com.rawlabs.das.sdk.java.test.mock;

import com.rawlabs.das.sdk.java.DASFunction;
import com.rawlabs.das.sdk.java.DASSdk;
import com.rawlabs.das.sdk.java.DASTable;
import com.rawlabs.protocol.das.FunctionDefinition;
import com.rawlabs.protocol.das.TableDefinition;

import java.util.List;

public class MockSdk implements DASSdk {

  @Override
  public List<TableDefinition> getTableDefinitions() {
    return List.of();
  }

  @Override
  public List<FunctionDefinition> getFunctionDefinitions() {
    return List.of();
  }

  @Override
  public DASTable getTable(String name) {
    return new MockTable();
  }

  @Override
  public DASFunction getFunction(String name) {
    return null;
  }
}
