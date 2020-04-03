package xyz.darke.darkpas.requesthandlers;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AbstractRequestHandlerTest {

    @Test
    public void parseQuery() {

        Map<String, String> testMap = new HashMap<>();
        testMap.put("id", "test");
        assertEquals(testMap, AbstractRequestHandler.parseQuery("id=test"));

        testMap.clear();
        testMap.put("id", "test=");
        assertEquals(testMap, AbstractRequestHandler.parseQuery("id=test="));
    }
}