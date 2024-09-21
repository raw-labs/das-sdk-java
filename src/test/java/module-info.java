import com.rawlabs.das.sdk.java.test.mock.MockSdkBuilder;

open module das.sdk.java.test {
  uses com.rawlabs.das.sdk.java.DASSdkBuilder;
  uses com.rawlabs.das.sdk.DASSdkBuilder;
  uses com.rawlabs.das.sdk.adapter.DASAdapterBuilder;

  requires das.sdk.java;
  requires org.junit.jupiter.api;
  requires org.junit.platform.commons;
  requires das.sdk.scala;
  requires raw.protocol.raw;
  requires protocol.das;
  requires raw.utils.core;
  requires scala.library;

  exports com.rawlabs.das.sdk.java.test;

  provides com.rawlabs.das.sdk.java.DASSdkBuilder with
      MockSdkBuilder;
}
