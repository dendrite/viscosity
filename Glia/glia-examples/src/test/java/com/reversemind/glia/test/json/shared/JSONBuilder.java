package com.reversemind.glia.test.json.shared;

import com.reversemind.glia.test.json.Settings;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple JSON builder for testing
 */
public class JSONBuilder {

    /**
     * @param addressObject - it's just a string - for example a city setName - Chicago
     * @return - JSON String {"searchAddress":"Chicago"}
     */
    public static String buildJSONQuery(String addressObject) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Settings.ADDRESS_SEARCH, addressObject);
        return build(map);
    }

    /**
     * Build JSON string from Java Map<String, Object>
     *
     * @param map
     * @return
     * @throws IOException
     */
    public static String build(Map<String, Object> map) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    /**
     * Build Java Map<String, Object> from JSON string
     *
     * @param JSONString
     * @return
     * @throws IOException
     */
    public static Map<String, Object> build(String JSONString) throws IOException {
        ObjectMapper mapperBack = new ObjectMapper();
        return mapperBack.readValue(JSONString, new TypeReference<Map<String, Object>>() {
        });
    }
}
