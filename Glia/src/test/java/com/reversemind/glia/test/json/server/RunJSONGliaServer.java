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

        GliaServer server = new GliaServer("GLIA_JSON_SERVER", Settings.SERVER_PORT, gliaPayloadProcessor, false);
        server.run();
    }
}
