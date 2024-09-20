package com.rawlabs.das.sdk.test;

import com.rawlabs.das.sdk.*;
import com.rawlabs.das.sdk.java.exceptions.DASSdkUnsupportedException;
import com.rawlabs.protocol.das.Qual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import scala.Option;
import scala.collection.Seq;
import scala.collection.Seq$;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing adapter logic")
public class AdapterTests {

    static DASSdkBuilder builder;
    static DASSdk sdk;

    @BeforeAll
    static void setup() {
        builder = ServiceLoader.load(com.rawlabs.das.sdk.DASSdkBuilder.class).iterator().next();
        sdk = builder.build(null, null);
    }

    @Test
    @DisplayName("Builder should be loaded")
    void builderLoaded() {
        assertNotNull(builder);
        assertEquals(builder.dasType(), "mock");
    }

    @Test
    @DisplayName("SDK should be loaded")
    void sdkLoaded() {
        assertNotNull(sdk);
    }

    @Test
    @DisplayName("Table should be loaded")
    void tableLoaded() {
        Option<DASTable> maybeTable = sdk.getTable("test");
        DASTable table = maybeTable.get();
        assertNotNull(maybeTable);
        assertTrue(maybeTable.isDefined());
        try (DASExecuteResult result =
                     table.execute(
                             (Seq<Qual>) Seq$.MODULE$.<Qual>empty(),
                             (Seq<String>) Seq$.MODULE$.<String>empty(),
                             Option.empty(),
                             Option.empty())) {
            assertTrue(result.hasNext());
            assertTrue(result.next().getDataMap().containsKey("column1"));
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Table should not support delete")
    void tableNotSupportingDelete() {
        Option<DASTable> maybeTable = sdk.getTable("test");
        DASTable table = maybeTable.get();
        assertNotNull(maybeTable);
        assertTrue(maybeTable.isDefined());
        assertThrows(DASSdkUnsupportedException.class, () -> table.delete(null));
    }
}
