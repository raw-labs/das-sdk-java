package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.Type;
import com.rawlabs.protocol.raw.Value;
import com.rawlabs.protocol.raw.ValueFloat;
import com.rawlabs.protocol.raw.ValueNull;

public class DASFloatHandler extends DASTypeHandlerChainNode {

  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasFloat()) {
      return checkNext(obj, type);
    } else if (obj == null && !type.getFloat().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    } else if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    } else {
      return Value.newBuilder().setFloat(ValueFloat.newBuilder().setV((Float) obj).build()).build();
    }
  }

  @Override
  public Object extractValue(Value value) {
    if (!value.hasFloat()) {
      return checkNext(value);
    }
    return value.getFloat().getV();
  }
}
