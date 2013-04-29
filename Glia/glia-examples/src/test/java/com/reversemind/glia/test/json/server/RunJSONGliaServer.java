package com.reversemind.glia.test.json.server;

import com.reversemind.glia.test.json.Settings;
import com.reversemind.glia.test.json.shared.IDoSomething;
import com.reversemind.glia.server.GliaPayloadProcessor;
import com.reversemind.glia.server.GliaServer;
import com.reversemind.glia.server.IGliaPayloadProcessor;

/**
 *
 */
public class RunJSONGliaServer {

    public static void main(String... args) throws InterruptedException {

        IGliaPayloadProcessor gliaPayloadProcessor = new GliaPayloadProcessor();
        gliaPayloadProcessor.registerPOJO(IDoSomething.class, ServerPojo.class);

        GliaServer gliaServer = new GliaServer("GLIA_JSON_SERVER", Settings.SERVER_PORT, gliaPayloadProcessor, false);
        gliaServer.run();


        int count = 0;
        // just wait for a minute 5 sec * 120 = 10 minutes
        while (count++ < 120) {
            Thread.sleep(5000);
            System.out.println(gliaServer.getMetrics());
        }

        gliaServer.shutdown();
    }
}
