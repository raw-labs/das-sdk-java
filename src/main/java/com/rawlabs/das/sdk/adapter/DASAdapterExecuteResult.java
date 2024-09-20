package com.rawlabs.das.sdk.adapter;

import com.rawlabs.das.sdk.DASExecuteResult;
import com.rawlabs.protocol.das.Row;

import java.io.IOException;

public class DASAdapterExecuteResult implements DASExecuteResult {

  private final com.rawlabs.das.sdk.java.DASExecuteResult result;

  DASAdapterExecuteResult(com.rawlabs.das.sdk.java.DASExecuteResult result) {
    this.result = result;
  }

  @Override
  public boolean hasNext() {
    return result.hasNext();
  }

  @Override
  public Row next() {
    return result.next();
  }

  @Override
  public void close() throws IOException {
    result.close();
  }
}
