package com.rawlabs.das.sdk.java.test.mock;

import com.rawlabs.das.sdk.java.DASExecuteResult;
import com.rawlabs.das.sdk.java.DASTable;
import com.rawlabs.das.sdk.java.RowsEstimation;
import com.rawlabs.protocol.das.Qual;
import com.rawlabs.protocol.das.Row;
import com.rawlabs.protocol.das.SortKey;
import com.rawlabs.protocol.raw.Value;
import com.rawlabs.protocol.raw.ValueInt;
import com.rawlabs.protocol.raw.ValueString;

import java.io.IOException;
import java.util.List;

public class MockTable implements DASTable {

  @Override
  public RowsEstimation getRelSize(List<Qual> quals, List<String> columns) {
    return new RowsEstimation(200, 200);
  }

  @Override
  public DASExecuteResult execute(
      List<Qual> quals, List<String> columns, List<SortKey> sortKeys, Long limit) {
    return new DASExecuteResult() {

      @Override
      public void close() throws IOException {}

      @Override
      public boolean hasNext() {
        return true;
      }

      @Override
      public Row next() {
        return Row.newBuilder()
            .putData(
                "column1", Value.newBuilder().setInt(ValueInt.newBuilder().setV(1).build()).build())
            .putData(
                "column2",
                Value.newBuilder()
                    .setString(ValueString.newBuilder().setV("row_tmp_" + 1).build())
                    .build())
            .build();
      }
    };
  }
}
