package com.reversemind.glia.test.servericediscovery;

import com.reversemind.glia.client.GliaClientServerDiscovery;
import com.reversemind.glia.client.ServerSelectorSimpleStrategy;
import org.junit.Ignore;
import org.junit.Test;

import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 2:27 PM
 *
 * @author
 * @since 1.0
 */
public class TestGliaClientServerDiscovery implements Serializable {

    @Ignore
    @Test
    public void testGliaClientSelfDiscovery() throws Exception {

        final String ZOOKEEPER_CONNECTION = "localhost:2181";
        final String SERVICE_BASE_PATH = "/baloo/services";
        final String SERVICE_NAME = "ADDRESS";

        final long clientTimeOut = 30000;
        GliaClientServerDiscovery client = new GliaClientServerDiscovery(ZOOKEEPER_CONNECTION, SERVICE_BASE_PATH, SERVICE_NAME, clientTimeOut, new ServerSelectorSimpleStrategy());

        client.start();

        Thread.sleep(1000 * 30);

        client.shutdown();
    }

}
