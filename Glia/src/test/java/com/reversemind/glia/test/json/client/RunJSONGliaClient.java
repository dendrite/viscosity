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

        GliaClient client = new GliaClient(Settings.SERVER_HOST, Settings.SERVER_PORT);
        client.run();

        // TODO make
        IDoSomething doSomething = (IDoSomething) ProxyFactory.getInstance(client).newProxyInstance(IDoSomething.class);

        String jsonString = doSomething.doExtraThing(JSONBuilder.buildJSONQuery("Chicago"));

        System.out.println("Server response: " + jsonString);



        for(int i=0;i<100;i++){
            jsonString = doSomething.doExtraThing(JSONBuilder.buildJSONQuery("Chicago" + i));
        }


        // let's parse a JSON string
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
        client.shutdown();
    }
}
