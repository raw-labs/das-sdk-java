package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.Type;
import com.rawlabs.protocol.raw.Value;
import com.rawlabs.protocol.raw.ValueList;
import com.rawlabs.protocol.raw.ValueNull;

import java.util.List;

public class DASListHandler extends DASTypeHandlerChainNode {

  DASTypeHandlerChainNode chainRoot;

  public DASListHandler(DASTypeHandlerChainNode chainRoot) {
    this.chainRoot = chainRoot;
  }

  @Override
  public Value createValue(Object obj, Type type) {
    if (!type.hasList()) {
      return checkNext(obj, type);
    }
    if (obj == null && !type.getList().getNullable()) {
      throw new IllegalArgumentException("non nullable value is null");
    }
    if (obj == null) {
      return Value.newBuilder().setNull(ValueNull.newBuilder().build()).build();
    }
    @SuppressWarnings("unchecked")
    List<Object> listOfValues = (List<Object>) obj;
    List<Value> values;
    if (type.getList().getInnerType().hasList()) {
      values =
          listOfValues.stream().map(i -> createValue(i, type.getList().getInnerType())).toList();
    } else {
      values =
          listOfValues.stream()
              .map(i -> chainRoot.createValue(i, type.getList().getInnerType()))
              .toList();
    }
    return Value.newBuilder().setList(ValueList.newBuilder().addAllValues(values)).build();
  }

  @Override
  public Object extractValue(Value value) {
    if (!value.hasList()) {
      return checkNext(value);
    }
    return value.getList().getValuesList().stream().map(i -> chainRoot.extractValue(i)).toList();
  }
}
