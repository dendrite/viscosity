package com.reversemind.glia.test.json.server;

import com.reversemind.glia.test.json.Settings;
import com.reversemind.glia.test.json.shared.IDoSomething;
import com.reversemind.glia.test.json.shared.JSONBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO on server side to process remote client request through a IDoSomething interface call
 */
public class ServerPojo implements IDoSomething {

    private static final Map<String, Object> ADDRESS_MAP = new HashMap<String, Object>() {
        {
            put("id#1", "St Louis, MO, USA");
            put("id#2", "Cansas Drive, Hopkinsville, KY, United States");
            put("id#3", "Chicago Ridge, IL, United States");
        }
    };

    /**
     * JSON string should be something like this {"searchAddress":"Chicago"}
     *
     * @param jsonString
     * @return
     */
    @Override
    public String doExtraThing(String jsonString) {
        try {

            LOG.debug("JSON string is:" + jsonString);
            Map<String, Object> queryMap = JSONBuilder.build(jsonString);

            LOG.debug("Going to search for address:" + queryMap.get(Settings.ADDRESS_SEARCH));

            Map<String, Object> responseMap = ADDRESS_MAP;
            responseMap.put("#id4", queryMap.get(Settings.ADDRESS_SEARCH));

            responseMap.put(Settings.SEARCH_STATUS, Settings.SEARCH_STATUS_OK);

            return JSONBuilder.build(responseMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get back an Error
        return "{" + Settings.SEARCH_STATUS + ":" + Settings.SEARCH_STATUS_ERROR + "}";
    }

    @Override
    public String doExtraThing(String jsonString, String otherParameter) {
        LOG.debug("Just for testing overrided functions - I've got a second parameter:" + otherParameter);
        return this.doExtraThing(jsonString);
    }

}
