package com.reversemind.glia.test.servericediscovery;

import com.reversemind.glia.client.GliaClientSelfDiscovery;
import com.reversemind.glia.client.IServerSelectorStrategy;
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
public class TestGliaClientSelfDiscovery implements Serializable {

    @Ignore
    @Test
    public void testGliaClientSelfDiscovery() throws Exception {

        final String ZOOKEEPER_CONNECTION = "localhost:2181";
        final String SERVICE_BASE_PATH = "/baloo/services";
        final String SERVICE_NAME = "ADDRESS";

        GliaClientSelfDiscovery client = new GliaClientSelfDiscovery(ZOOKEEPER_CONNECTION, SERVICE_BASE_PATH, SERVICE_NAME, new ServerSelectorSimpleStrategy());

        client.run();

        Thread.sleep(1000 * 30);

        client.shutdown();
    }

}
