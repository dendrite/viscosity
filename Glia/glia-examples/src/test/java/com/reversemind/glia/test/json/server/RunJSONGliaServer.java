package com.reversemind.glia.test.json.server;

import com.reversemind.glia.server.*;
import com.reversemind.glia.test.json.Settings;
import com.reversemind.glia.test.json.shared.IDoSomething;

/**
 *
 */
public class RunJSONGliaServer {

    public static void main(String... args) throws InterruptedException {

        IGliaPayloadProcessor gliaPayloadProcessor = new GliaPayloadProcessor();
        gliaPayloadProcessor.registerPOJO(IDoSomething.class, ServerPojo.class);

        IGliaServer gliaServer = GliaServerFactory.builder(GliaServerFactory.Builder.Type.SIMPLE)
                .payloadWorker(gliaPayloadProcessor)
                .name("GLIA_JSON_SERVER")
                .port(Settings.SERVER_PORT)
                .keepClientAlive(false)
                .build();

        gliaServer.start();

        int count = 0;
        // just wait for a minute 5 sec * 120 = 10 minutes
        while (count++ < 120) {
            Thread.sleep(5000);
            System.out.println(gliaServer.getMetrics());
        }

        gliaServer.shutdown();
    }
}
