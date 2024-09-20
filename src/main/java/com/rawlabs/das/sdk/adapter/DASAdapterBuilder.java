package com.rawlabs.das.sdk.adapter;

import com.rawlabs.das.sdk.DASSdk;
import com.rawlabs.das.sdk.DASSdkBuilder;
import com.rawlabs.utils.core.RawSettings;
import scala.collection.immutable.Map;

import java.util.ServiceLoader;

public class DASAdapterBuilder implements DASSdkBuilder {

  private final com.rawlabs.das.sdk.java.DASSdkBuilder builder;

  public DASAdapterBuilder() {
    builder = ServiceLoader.load(com.rawlabs.das.sdk.java.DASSdkBuilder.class).iterator().next();
  }

  @Override
  public String dasType() {
    return builder.getDasType();
  }

  @Override
  public DASSdk build(Map<String, String> options, RawSettings settings) {
    return new DASAdapterSdk(
        builder.build(scala.collection.JavaConverters.mapAsJavaMap(options), settings));
  }
}
