package com.rawlabs.das.sdk.java;

import com.rawlabs.utils.core.RawSettings;

import java.util.Map;

public interface DASSdkBuilder {
  String getDasType();

  DASSdk build(Map<String, String> options, RawSettings settings);
}
