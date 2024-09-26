package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.Type;
import com.rawlabs.protocol.raw.Value;
import com.rawlabs.protocol.raw.ValueLong;
import com.rawlabs.protocol.raw.ValueNull;

public class DASLongHandler extends DASTypeHandlerChainNode {

  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasLong()) {
      return checkNext(obj, type);
    } else if (obj == null && !type.getLong().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    } else if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    } else {
      return Value.newBuilder().setLong(ValueLong.newBuilder().setV((Long) obj).build()).build();
    }
  }

  @Override
  public Object extractValue(Value value) {
    if (!value.hasLong()) {
      return checkNext(value);
    }
    return value.getLong().getV();
  }
}
