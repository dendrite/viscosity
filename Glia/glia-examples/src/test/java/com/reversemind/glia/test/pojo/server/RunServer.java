package com.reversemind.glia.test.pojo.server;

import com.reversemind.glia.server.GliaPayloadProcessor;
import com.reversemind.glia.server.GliaServer;
import com.reversemind.glia.test.pojo.server.SimplePojo;
import com.reversemind.glia.test.pojo.shared.ISimplePojo;

import java.io.Serializable;

/**
 * Date: 4/24/13
 * Time: 5:06 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class RunServer implements Serializable {

    public static void main(String... args) {

        GliaPayloadProcessor gliaPayloadProcessor = new GliaPayloadProcessor();
        gliaPayloadProcessor.registerPOJO(ISimplePojo.class, SimplePojo.class);

        int port = 7000;
        GliaServer server = new GliaServer(port, gliaPayloadProcessor, false);
        System.out.println("Started on port:" + server.getPort());
        server.run();

    }

}
