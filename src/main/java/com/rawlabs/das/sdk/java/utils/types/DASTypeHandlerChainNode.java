package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.Type;
import com.rawlabs.protocol.raw.Value;

public abstract class DASTypeHandlerChainNode {

  private DASTypeHandlerChainNode next;

  public static DASTypeHandlerChainNode link(
      DASTypeHandlerChainNode first, DASTypeHandlerChainNode... chain) {
    DASTypeHandlerChainNode head = first;
    for (DASTypeHandlerChainNode nextInChain : chain) {
      head.next = nextInChain;
      head = nextInChain;
    }
    return first;
  }

  public abstract Value createValue(Object obj, Type type);

  public abstract Object extractValue(Value value);

  protected Value checkNext(Object obj, Type type) {
    if (next == null) {
      throw new IllegalArgumentException("type not supported");
    }
    return next.createValue(obj, type);
  }

  protected Object checkNext(Value value) {
    if (next == null) {
      throw new IllegalArgumentException("type not supported");
    }
    return next.extractValue(value);
  }
}
