package com.rawlabs.das.sdk.adapter;

import com.rawlabs.das.sdk.DASFunction;
import com.rawlabs.protocol.raw.Value;
import scala.collection.immutable.Map;

public class DASAdapterFunction implements DASFunction {

  private final com.rawlabs.das.sdk.java.DASFunction function;

  DASAdapterFunction(com.rawlabs.das.sdk.java.DASFunction function) {
    this.function = function;
  }

  @Override
  public Value execute(Map<String, Value> args) {
    return function.execute(scala.collection.JavaConverters.mapAsJavaMap(args));
  }
}
