package com.rawlabs.das.sdk.test.mock;

import com.rawlabs.das.sdk.java.DASSdk;
import com.rawlabs.das.sdk.java.DASSdkBuilder;
import com.rawlabs.utils.core.RawSettings;

import java.util.Map;

public class MockSdkBuilder implements DASSdkBuilder {

  @Override
  public String getDasType() {
    return "mock";
  }

  @Override
  public DASSdk build(Map<String, String> options, RawSettings settings) {
    return new MockSdk();
  }
}
