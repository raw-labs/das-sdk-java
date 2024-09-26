package com.rawlabs.das.sdk.java.utils.types;

import com.google.protobuf.ByteString;
import com.rawlabs.protocol.raw.*;

public class DASBinaryHandler extends DASTypeHandlerChainNode {

  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasBinary()) {
      return checkNext(obj, type);
    } else if (obj == null && !type.getBinary().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    } else if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    } else {
      return Value.newBuilder()
          .setBinary(ValueBinary.newBuilder().setV(ByteString.copyFrom((byte[]) obj)).build())
          .build();
    }
  }

  @Override
  public Object extractValue(Value value) {
    if (!value.hasBinary()) {
      return checkNext(value);
    }
    return value.getBinary().getV().toByteArray();
  }
}
