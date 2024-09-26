package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.Type;
import com.rawlabs.protocol.raw.Value;
import com.rawlabs.protocol.raw.ValueBool;
import com.rawlabs.protocol.raw.ValueNull;

public class DASBoolHandler extends DASTypeHandlerChainNode {

  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasBool()) {
      return checkNext(obj, type);
    } else if (obj == null && !type.getBool().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    } else if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    } else {
      return Value.newBuilder().setBool(ValueBool.newBuilder().setV((Boolean) obj).build()).build();
    }
  }

  @Override
  public Object extractValue(Value value) {
    if (!value.hasBool()) {
      return checkNext(value);
    }
    return value.getBool().getV();
  }
}
