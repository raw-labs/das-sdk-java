module das.sdk.java {
  uses com.rawlabs.das.sdk.java.DASSdkBuilder;

  exports com.rawlabs.das.sdk.java;
  exports com.rawlabs.das.sdk.adapter to
      das.sdk.scala,
      das.sdk.java.test;

  requires das.sdk.scala;
  requires protocol.das;
  requires raw.protocol.raw;
  requires raw.utils.core;
  requires scala.library;

  provides com.rawlabs.das.sdk.DASSdkBuilder with
      com.rawlabs.das.sdk.adapter.DASAdapterBuilder;
}
