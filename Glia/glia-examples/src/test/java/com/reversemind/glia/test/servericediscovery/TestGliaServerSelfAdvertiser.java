package com.reversemind.glia.test.servericediscovery;

import com.reversemind.glia.server.GliaServerSelfAdvertiser;
import com.reversemind.glia.server.IGliaPayloadProcessor;
import org.junit.Ignore;
import org.junit.Test;

import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 11:45 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class TestGliaServerSelfAdvertiser implements Serializable {

    @Ignore
    @Test
    public void testGliaServerSelfAdvertiser() throws InterruptedException {

        final String ZOOKEEPER_CONNECTION = "localhost:2181";
        final String SERVICE_BASE_PATH = "/baloo/services";
        final String SERVICE_NAME = "ADDRESS";

        GliaServerSelfAdvertiser server01 = new GliaServerSelfAdvertiser(
                ZOOKEEPER_CONNECTION,
                SERVICE_BASE_PATH,
                SERVICE_NAME,
                null,
                false,
                "INSTANCE.001");

        GliaServerSelfAdvertiser server02 = new GliaServerSelfAdvertiser(
                ZOOKEEPER_CONNECTION,
                SERVICE_BASE_PATH,
                SERVICE_NAME,
                null,
                false,
                "INSTANCE.002");

        server01.run();
        server02.run();

        Thread.sleep(1000 * 60 * 5);

        server01.shutdown();
        server02.shutdown();
    }

}
