package com.rawlabs.das.sdk.java;

import com.rawlabs.das.sdk.java.exceptions.DASSdkUnsupportedException;
import com.rawlabs.protocol.das.Qual;
import com.rawlabs.protocol.das.Row;
import com.rawlabs.protocol.das.SortKey;
import com.rawlabs.protocol.raw.Value;

import javax.annotation.Nullable;
import java.util.List;

public interface DASTable {

  /**
   * Method called from the planner to estimate the resulting relation size for a scan.
   *
   * @param quals list of Qual instances describing the filters applied to this scan
   * @param columns list of columns that must be returned
   * @return RowsEstimation instance with the estimated number of rows and the estimated size in
   *     bytes
   */
  RowsEstimation getRelSize(List<Qual> quals, List<String> columns);

  /**
   * Method called from the planner to ask the FDW what are the sorts it can enforce, to avoid
   * PostgreSQL to sort the data after retrieving all the rows. These sorts can come from explicit
   * <code>ORDER BY</code> clauses, but also <code>GROUP BY</code> and <code>DISTINCT</code>
   * clauses.
   *
   * <p>The FDW has to inspect every sort, and respond which one are handled. The sorts are
   * cumulative. For example:
   *
   * <p><code>col1 ASC</code>
   *
   * <p><code>col2 DESC</code>
   *
   * <p>means that the FDW must render the tuples sorted by col1 ascending and col2 descending.
   *
   * <p>If this method returns an empty list, PostgreSQL will sort the data after retrieving all the
   * rows.
   *
   * <p>If this method returns a list with elements, then it <b>must</b> return the tuples sorted by
   * the key in the <code>execute</code> and <code>explain</code> method
   *
   * @param sortKeys list of SortKey representing all the sorts the query must enforce
   * @return list of cumulative SortKey, for which the FDW can enforce the sort.
   */
  default List<SortKey> canSort(List<SortKey> sortKeys) {
    return List.of();
  }

  /**
   * Method called from the planner to add additional Path to the planner. By default, the planner
   * generates an (unparameterized) path, which can be reasoned about like a SequentialScan,
   * optionally filtered.
   *
   * <p>This method allows the implementor to declare other Paths, corresponding to faster access
   * methods for specific attributes. Such a parameterized path can be reasoned about like an
   * IndexScan.
   *
   * <p>For example, with the following query::
   *
   * <p>select * from foreign_table inner join local_table using(id);
   *
   * <p>where foreign_table is a foreign table containing 100000 rows, and local_table is a regular
   * table containing 100 rows.
   *
   * <p>The previous query would probably be transformed to a plan similar to this one::
   *
   * <p>┌────────────────────────────────────────────────────────────────────────────────────┐ │
   * QUERY PLAN │
   * ├────────────────────────────────────────────────────────────────────────────────────┤ │ Hash
   * Join (cost=57.67..4021812.67 rows=615000 width=68) │ │ Hash Cond: (foreign_table.id =
   * local_table.id) │ │ -> Foreign Scan on foreign_table (cost=20.00..4000000.00 rows=100000
   * width=40) │ │ -> Hash (cost=22.30..22.30 rows=1230 width=36) │ │ -> Seq Scan on local_table
   * (cost=0.00..22.30 rows=1230 width=36) │
   * └────────────────────────────────────────────────────────────────────────────────────┘
   *
   * <p>But with a parameterized path declared on the id key, with the knowledge that this key is
   * unique on the foreign side, the following plan might get chosen::
   *
   * <p>┌───────────────────────────────────────────────────────────────────────┐ │ QUERY PLAN │
   * ├───────────────────────────────────────────────────────────────────────┤ │ Nested Loop
   * (cost=20.00..49234.60 rows=615000 width=68) │ │ -> Seq Scan on local_table (cost=0.00..22.30
   * rows=1230 width=36) │ │ -> Foreign Scan on remote_table (cost=20.00..40.00 rows=1 width=40)│ │
   * Filter: (id = local_table.id) │
   * └───────────────────────────────────────────────────────────────────────┘
   *
   * <p>Returns: A list of tuples of the form: (key_columns, expected_rows), where key_columns is a
   * tuple containing the columns on which the path can be used, and expected_rows is the number of
   * rows this path might return for a simple lookup. For example, the return value corresponding to
   * the previous scenario would be::
   *
   * <p>[(('id',), 1)]
   *
   * @return list of tuples of the form: (key_columns, expected_rows), where key_columns is a tuple
   *     containing the columns on which the path can be used, and expected_rows is the number of
   *     rows this path might return for a simple lookup.
   */
  default List<KeyColumns> getPathKeys() {
    return List.of();
  }

  default List<String> explain(
      List<Qual> quals,
      List<String> columns,
      @Nullable List<SortKey> sortKeys,
      @Nullable Long limit,
      boolean verbose) {
    return List.of();
  }

  /**
   * A SELECT statement is executed by calling the execute method. Quals, colums, sortKeys, limit
   * help to filter, project, sort and limit the results. Implementing Quals, colums and limit is
   * optional, but they can be useful to optimize the query execution.
   *
   * <p>Example:
   *
   * <p>lets assume that this execute method calls an API that supports filtering, projection and
   * limiting. If the params are ignored, the API will return all the rows in the table and postgres
   * will do the filtering, projection and limiting. If the params are used and propagated to the
   * backend of the API, the results will be filtered, projected and limited, and the data volume
   * transferred will be minimized. If sortKeys are not empty, the execute method **has to** return
   * the results sorted by the keys in the same order as the keys in the sortKeys param, PostgreSQL
   * won't do it. sortKeys are correlated to canSort method. Depending on what canSort returns, this
   * param will either contain the keys or be empty.
   *
   * @param quals - Qualifiers for filtering the rows
   * @param columns - Columns to project
   * @param sortKeys - Sort keys. The sort keys are correlated to canSort method. Depending on what
   *     canSort returns, this param will either contain the keys or be empty. If this param has
   *     values, then the execute method **has to** return the results sorted by the keys in the
   *     same order as the keys in the sortKeys param, postgres will assume that the results are
   *     sorted.
   * @param limit - Limit the number of rows returned
   * @return result of the execution
   */
  DASExecuteResult execute(
      List<Qual> quals,
      List<String> columns,
      @Nullable List<SortKey> sortKeys,
      @Nullable Long limit);

  default String getUniqueColumn() {
    throw new DASSdkUnsupportedException();
  }

  default int getModifyBatchSize() {
    return 1;
  }

  default Row insertRow(Row row) {
    throw new DASSdkUnsupportedException();
  }

  default List<Row> insertRows(List<Row> rows) {
    throw new DASSdkUnsupportedException();
  }

  /**
   * Update a row with the new values
   *
   * @param rowId the row id (defined in getUniqueColumn method)
   * @param newValues the new values
   * @return the updated row
   */
  default Row updateRow(Value rowId, Row newValues) {
    throw new DASSdkUnsupportedException();
  }

  /**
   * Delete a row
   *
   * @param rowId the row id (defined in getUniqueColumn method)
   */
  default void deleteRow(Value rowId) {
    throw new DASSdkUnsupportedException();
  }
}
