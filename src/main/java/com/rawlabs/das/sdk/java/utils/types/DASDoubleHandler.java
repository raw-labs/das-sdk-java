package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.Type;
import com.rawlabs.protocol.raw.Value;
import com.rawlabs.protocol.raw.ValueDouble;
import com.rawlabs.protocol.raw.ValueNull;

public class DASDoubleHandler extends DASTypeHandlerChainNode {

  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasDouble()) {
      return checkNext(obj, type);
    } else if (obj == null && !type.getDouble().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    } else if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    } else {
      return Value.newBuilder()
          .setDouble(ValueDouble.newBuilder().setV((Double) obj).build())
          .build();
    }
  }

  @Override
  public Object extractValue(Value value) {
    if (!value.hasDouble()) {
      return checkNext(value);
    }
    return value.getDouble().getV();
  }
}
