package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.Type;
import com.rawlabs.protocol.raw.Value;
import com.rawlabs.protocol.raw.ValueInt;
import com.rawlabs.protocol.raw.ValueNull;

public class DASShortHandler extends DASTypeHandlerChainNode {
  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasInt()) {
      return checkNext(obj, type);
    }
    if (obj == null && !type.getInt().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    }
    if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    }
    return Value.newBuilder().setInt(ValueInt.newBuilder().setV((Integer) obj).build()).build();
  }

  @Override
  public Object extractValue(Value value) {
    if (!value.hasInt()) {
      return checkNext(value);
    }
    return value.getInt().getV();
  }
}
