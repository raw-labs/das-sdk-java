package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.protocol.raw.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rawlabs.das.sdk.java.utils.TypeFactory.createListType;
import static com.rawlabs.das.sdk.java.utils.TypeFactory.createStringType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Value Factory Tests")
public class DASTypeHandlerChainTest {

  @Test
  @DisplayName("Test create string value")
  void testCreateStringValue() {
    DASTypeHandlerChainNode chain = DASTypeHandlerDefaultChain.getDefaultChain();
    Value v = chain.createValue("test", createStringType());
    assertEquals("test", v.getString().getV());
  }

  @Test
  @DisplayName("Test create null value")
  void testCreateNullValue() {
    DASTypeHandlerChainNode chain = DASTypeHandlerDefaultChain.getDefaultChain();
    Value v = chain.createValue(null, createStringType());
    assertTrue(v.hasNull());
  }

  @Test
  @DisplayName("Test create list value")
  void testCreateListValue() {
    DASTypeHandlerChainNode chain = DASTypeHandlerDefaultChain.getDefaultChain();
    List<List<String>> list = List.of(List.of("a", "b", "c"));
    Value v = chain.createValue(list, createListType(createListType(createStringType())));
    assertTrue(v.hasList());
    assertEquals(1, v.getList().getValuesCount());
    assertEquals(3, v.getList().getValues(0).getList().getValuesCount());
  }
}
