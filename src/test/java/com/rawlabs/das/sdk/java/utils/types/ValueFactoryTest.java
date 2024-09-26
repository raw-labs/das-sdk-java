package com.rawlabs.das.sdk.java.utils.types;

import com.rawlabs.das.sdk.java.utils.factory.ValueFactory;
import com.rawlabs.protocol.raw.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rawlabs.das.sdk.java.utils.factory.TypeFactory.createListType;
import static com.rawlabs.das.sdk.java.utils.factory.TypeFactory.createStringType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Value Factory Tests")
public class ValueFactoryTest {

  @Test
  @DisplayName("Test create string value")
  void testCreateStringValue() {
    Value v = ValueFactory.getInstance().createValue("test", createStringType());
    assertEquals("test", v.getString().getV());
  }

  @Test
  @DisplayName("Test create null value")
  void testCreateNullValue() {
    Value v = ValueFactory.getInstance().createValue(null, createStringType());
    assertTrue(v.hasNull());
  }

  @Test
  @DisplayName("Test create list value")
  void testCreateListValue() {
    List<List<String>> list = List.of(List.of("a", "b", "c"));
    Value v =
        ValueFactory.getInstance()
            .createValue(list, createListType(createListType(createStringType())));
    assertTrue(v.hasList());
    assertEquals(1, v.getList().getValuesCount());
    assertEquals(3, v.getList().getValues(0).getList().getValuesCount());
  }
}
