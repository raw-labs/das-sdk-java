package com.rawlabs.das.sdk.adapter;

import com.rawlabs.das.sdk.DASFunction;
import com.rawlabs.das.sdk.DASSdk;
import com.rawlabs.das.sdk.DASTable;
import com.rawlabs.protocol.das.FunctionDefinition;
import com.rawlabs.protocol.das.TableDefinition;
import scala.Option;
import scala.collection.JavaConverters;
import scala.collection.Seq;

public class DASAdapterSdk implements DASSdk {

  private final com.rawlabs.das.sdk.java.DASSdk sdk;

  DASAdapterSdk(com.rawlabs.das.sdk.java.DASSdk sdk) {
    this.sdk = sdk;
  }

  @Override
  public Seq<TableDefinition> tableDefinitions() {
    return JavaConverters.collectionAsScalaIterable(sdk.getTableDefinitions()).toSeq();
  }

  @Override
  public Seq<FunctionDefinition> functionDefinitions() {
    return JavaConverters.collectionAsScalaIterable(sdk.getFunctionDefinitions()).toSeq();
  }

  @Override
  public Option<DASTable> getTable(String name) {
    com.rawlabs.das.sdk.java.DASTable table = sdk.getTable(name);
    if (table != null) {
      return Option.apply(new DASAdapterTable(table));
    }
    return Option.empty();
  }

  @Override
  public Option<DASFunction> getFunction(String name) {
    com.rawlabs.das.sdk.java.DASFunction function = sdk.getFunction(name);
    if (function != null) {
      return Option.apply(new DASAdapterFunction(function));
    }
    return Option.empty();
  }
}
