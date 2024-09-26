package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.*;

public class DASDecimalHandler extends DASTypeHandlerChainNode {

  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasDecimal()) {
      return checkNext(obj, type);
    } else if (obj == null && !type.getDecimal().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    } else if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    } else {
      return Value.newBuilder()
          .setDecimal(ValueDecimal.newBuilder().setV((String) obj).build())
          .build();
    }
  }

  @Override
  public Object extractValue(Value value) {
    if (!value.hasDecimal()) {
      return checkNext(value);
    }
    return value.getDecimal().getV();
  }
}
