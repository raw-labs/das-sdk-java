package com.rawlabs.das.sdk.java.utils.types;

import static com.rawlabs.das.sdk.java.utils.types.DASTypeHandlerChainNode.link;

public class DASTypeHandlerDefaultChain {
  private static final DASTypeHandlerChainNode defaultChain =
      link(
          new DASBoolHandler(),
          new DASIntHandler(),
          new DASShortHandler(),
          new DASLongHandler(),
          new DASFloatHandler(),
          new DASDoubleHandler(),
          new DASStringHandler(),
          new DASDecimalHandler(),
          new DASBinaryHandler());

  public static DASTypeHandlerChainNode getDefaultChain() {
    return link(new DASListHandler(defaultChain), defaultChain);
  }
}
