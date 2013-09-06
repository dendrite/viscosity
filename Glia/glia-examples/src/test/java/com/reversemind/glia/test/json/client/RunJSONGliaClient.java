package com.reversemind.glia.test.json.client;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.test.json.Settings;
import com.reversemind.glia.test.json.shared.IDoSomething;
import com.reversemind.glia.test.json.shared.JSONBuilder;
import com.reversemind.glia.proxy.ProxyFactory;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public class RunJSONGliaClient {

    public static void main(String... args) throws Exception {

        //
        GliaClient gliaClient = new GliaClient(Settings.SERVER_HOST, Settings.SERVER_PORT);
        gliaClient.start();

        // create proxy for remote service
        IDoSomething doSomething = (IDoSomething) ProxyFactory.getInstance().newProxyInstance(gliaClient, IDoSomething.class);

        // call remote server
        String jsonString = doSomething.doExtraThing(JSONBuilder.buildJSONQuery("Chicago"));
        System.out.println("Server response: " + jsonString);



        System.out.println("\n\nMake a second interface:");
        // call remote server
        jsonString = doSomething.doExtraThing(JSONBuilder.buildJSONQuery("Chicago"), "SIMPLE STRING");

        System.out.println("Server response: " + jsonString);
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
            System.out.println("Yep!");

            Set<String> keys = serverResponseMap.keySet();
            for (String key : keys) {
                System.out.println(key + ":" + serverResponseMap.get(key));
            }
        }

        //Thread.sleep(3000);
        gliaClient.shutdown();
    }
}
