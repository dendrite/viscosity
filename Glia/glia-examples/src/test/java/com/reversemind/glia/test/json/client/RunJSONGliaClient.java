package com.reversemind.glia.test.json.client;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.test.json.Settings;
import com.reversemind.glia.test.json.shared.IDoSomething;
import com.reversemind.glia.test.json.shared.JSONBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public class RunJSONGliaClient {

    private static final Logger LOG = LoggerFactory.getLogger(RunJSONGliaClient.class);

    public static void main(String... args) throws Exception {

        //
        GliaClient gliaClient = new GliaClient(Settings.SERVER_HOST, Settings.SERVER_PORT);
        gliaClient.start();

        // create proxy for remote service
        IDoSomething doSomething = (IDoSomething) ProxyFactory.getInstance().newProxyInstance(gliaClient, IDoSomething.class);

        // call remote server
        String jsonString = doSomething.doExtraThing(JSONBuilder.buildJSONQuery("Chicago"));
        LOG.debug("Server response: " + jsonString);


        LOG.debug("\n\nMake a second interface:");
        // call remote server
        jsonString = doSomething.doExtraThing(JSONBuilder.buildJSONQuery("Chicago"), "SIMPLE STRING");

        LOG.debug("Server response: " + jsonString);
//
//        // jut test yourself for little highload
//        for(int i=0;i<1;i++){
//            jsonString = doSomething.doExtraThing(JSONBuilder.buildJSONQuery("Chicago" + i));
//        }

        // let's parse a JSON string from server
        Map<String, Object> serverResponseMap = JSONBuilder.build(jsonString);

        // get status from response
        String serverResponseStatus = (String) serverResponseMap.get(Settings.SEARCH_STATUS);
        if (serverResponseStatus.equals(Settings.SEARCH_STATUS_OK)) {
            LOG.debug("Yep!");

            Set<String> keys = serverResponseMap.keySet();
            for (String key : keys) {
                LOG.debug(key + ":" + serverResponseMap.get(key));
            }
        }

        //Thread.sleep(3000);
        gliaClient.shutdown();
    }
}
