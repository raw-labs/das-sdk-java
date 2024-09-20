package com.rawlabs.das.sdk.java;

import com.rawlabs.protocol.raw.Value;

import java.util.Map;

public interface DASFunction {
  Value execute(Map<String, Value> args);
}
