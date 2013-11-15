package com.reversemind.glia.test.json.server;

import com.reversemind.glia.server.GliaPayloadProcessor;
import com.reversemind.glia.server.GliaServerFactory;
import com.reversemind.glia.server.IGliaPayloadProcessor;
import com.reversemind.glia.server.IGliaServer;
import com.reversemind.glia.test.json.Settings;
import com.reversemind.glia.test.json.shared.IDoSomething;

/**
 *
 */
public class RunJSONGliaServer {

    public static void main(String... args) throws InterruptedException {

        IGliaPayloadProcessor gliaPayloadProcessor = new GliaPayloadProcessor();
        gliaPayloadProcessor.registerPOJO(IDoSomething.class, ServerPojo.class);

        IGliaServer gliaServer = GliaServerFactory.builder()
                .setPayloadWorker(gliaPayloadProcessor)
                .setName("GLIA_JSON_SERVER")
                .setPort(Settings.SERVER_PORT)
                .setKeepClientAlive(true)
                .setAutoSelectPort(false)
                .build();

        gliaServer.start();

        int count = 0;
        // just wait for a minute 5 sec * 120 = 10 minutes
        while (count++ < 120) {
            Thread.sleep(5000);
            LOG.debug(gliaServer.getMetrics());
        }

        gliaServer.shutdown();
    }
}
