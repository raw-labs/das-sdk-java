package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.*;

public class DASStringHandler extends DASTypeHandlerChainNode {

  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasString()) {
      return checkNext(obj, type);
    }
    if (obj == null && !type.getString().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    }
    if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    }
    return Value.newBuilder()
        .setString(ValueString.newBuilder().setV((String) obj).build())
        .build();
  }

  @Override
  public String extractValue(Value value) {
    if (!value.hasString()) {
      return (String) checkNext(value);
    }
    return value.getString().getV();
  }
}
