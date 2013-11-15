package com.reversemind.glia.test.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Test JSON generation with
 */
public class TestJSON {

    private static final Logger LOG = LoggerFactory.getLogger(TestJSON.class);

    /**
     * Generate JSON string from Java Map and read a JSON string back into Map
     *
     * @throws IOException
     */

    @Ignore
    @Test
    public void testJSON() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> addressMap = new HashMap<String, Object>();

        addressMap.put("id#1", "St Louis, MO, USA");
        addressMap.put("id#2", "Cansas Drive, Hopkinsville, KY, United States");
        addressMap.put("id#3", "Chicago Ridge, IL, United States");

        List<Object> list = new ArrayList<Object>();
        list.add("001");
        list.add("002");
        list.add("003");

        addressMap.put("versions", list);

        String jsonString = mapper.writeValueAsString(addressMap);
        LOG.debug("" + jsonString);


        // Read JSON string back
        ObjectMapper mapperBack = new ObjectMapper();
        Map<String, Object> addressMapBack = mapperBack.readValue(jsonString, new TypeReference<Map<String, Object>>() {
        });

        Set<String> keys = addressMapBack.keySet();
        for (String key : keys) {
            LOG.debug(key + ":" + addressMapBack.get(key));
        }

    }

}
