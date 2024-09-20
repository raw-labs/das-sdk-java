package com.rawlabs.das.sdk.adapter;

import com.rawlabs.das.sdk.DASExecuteResult;
import com.rawlabs.das.sdk.java.KeyColumns;
import com.rawlabs.das.sdk.java.RowsEstimation;
import com.rawlabs.protocol.das.Qual;
import com.rawlabs.protocol.das.Row;
import com.rawlabs.protocol.das.SortKey;
import com.rawlabs.protocol.raw.Value;
import scala.Option;
import scala.Tuple2;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.List;

public class DASAdapterTable implements com.rawlabs.das.sdk.DASTable {

  private final com.rawlabs.das.sdk.java.DASTable table;

  DASAdapterTable(com.rawlabs.das.sdk.java.DASTable table) {
    this.table = table;
  }

  @Override
  public Tuple2<Object, Object> getRelSize(Seq<Qual> quals, Seq<String> columns) {
    RowsEstimation estimation =
        table.getRelSize(
            JavaConverters.seqAsJavaList(quals), JavaConverters.seqAsJavaList(columns));
    return new Tuple2<>(estimation.expectedNumberOfRows(), estimation.avgRowWidthInBytes());
  }

  @Override
  public Seq<String> explain(
      Seq<Qual> quals,
      Seq<String> columns,
      Option<Seq<SortKey>> maybeSortKeys,
      Option<Object> maybeLimit,
      boolean verbose) {
    List<SortKey> sortKeys =
        maybeSortKeys.isDefined() ? JavaConverters.seqAsJavaList(maybeSortKeys.get()) : null;
    Long limit = maybeLimit.isDefined() ? (Long) maybeLimit.get() : null;

    List<String> explain =
        table.explain(
            JavaConverters.seqAsJavaList(quals),
            JavaConverters.seqAsJavaList(columns),
            sortKeys,
            limit,
            verbose);

    return JavaConverters.asScalaBuffer(explain).toSeq();
  }

  @Override
  public Seq<Tuple2<Seq<String>, Object>> getPathKeys() {
    List<KeyColumns> keyColumns = table.getPathKeys();
    Seq<Tuple2<Seq<String>, Object>> pathKeys;
    return JavaConverters.collectionAsScalaIterable(
            keyColumns.stream()
                .map(
                    kc ->
                        new Tuple2<>(
                            JavaConverters.asScalaBuffer(kc.columns()).toSeq(),
                            (Object) kc.expectedRows()))
                .toList())
        .toSeq();
  }

  @Override
  public Row insert(Row row) {
    return table.insertRow(row);
  }

  @Override
  public Row update(Value rowId, Row newValues) {
    return table.updateRow(rowId, newValues);
  }

  @Override
  public Seq<SortKey> canSort(Seq<SortKey> sortKeys) {
    return JavaConverters.collectionAsScalaIterable(
            table.canSort(JavaConverters.seqAsJavaList(sortKeys)))
        .toSeq();
  }

  @Override
  public String uniqueColumn() {
    return table.getUniqueColumn();
  }

  @Override
  public DASExecuteResult execute(
      Seq<Qual> quals,
      Seq<String> columns,
      Option<Seq<SortKey>> maybeSortKeys,
      Option<Object> maybeLimit) {
    List<SortKey> sortKeys =
        maybeSortKeys.isDefined() ? JavaConverters.seqAsJavaList(maybeSortKeys.get()) : null;
    Long limit = maybeLimit.isDefined() ? (Long) maybeLimit.get() : null;

    com.rawlabs.das.sdk.java.DASExecuteResult result =
        table.execute(
            JavaConverters.seqAsJavaList(quals),
            JavaConverters.seqAsJavaList(columns),
            sortKeys,
            limit);

    return new DASAdapterExecuteResult(result);
  }

  @Override
  public void delete(Value rowId) {
    table.deleteRow(rowId);
  }

  @Override
  public int modifyBatchSize() {
    return table.getModifyBatchSize();
  }

  @Override
  public Seq<Row> bulkInsert(Seq<Row> rows) {
    return JavaConverters.collectionAsScalaIterable(
            table.insertRows(JavaConverters.seqAsJavaList(rows)))
        .toSeq();
  }
}
