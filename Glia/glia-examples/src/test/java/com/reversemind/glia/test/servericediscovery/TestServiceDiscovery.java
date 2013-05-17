package com.reversemind.glia.test.servericediscovery;

import com.reversemind.glia.server.*;
import com.reversemind.glia.servicediscovery.ServiceDiscoverer;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadataBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/30/13
 * Time: 10:41 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class TestServiceDiscovery implements Serializable {

    @Ignore
    @Test
    public void testAdvertise() throws InterruptedException, IOException {

        final String SERVICE_NAME= "ADDRESS";
        final String ZOOKEEPER_CONNECTION_STRING = "localhost:2181";
        final String BASE_PATH = "/baloo/services/" + SERVICE_NAME;

        ServiceDiscoverer discoverer = new ServiceDiscoverer(ZOOKEEPER_CONNECTION_STRING, BASE_PATH);

        //IGliaServer serverOne = new GliaServer(SERVICE_NAME, null, false);

        IGliaPayloadProcessor gliaPayloadProcessor = new GliaPayloadProcessor();

        IGliaServer serverOne = GliaServerFactory.builder()
                .setPayloadWorker(gliaPayloadProcessor)
                .setName(SERVICE_NAME)
                .setZookeeperHosts(ZOOKEEPER_CONNECTION_STRING)
                .setServiceBasePath(BASE_PATH)
                .setAutoSelectPort(true)
                .setKeepClientAlive(false)
                .build();

        IGliaServer serverTwo = GliaServerFactory.builder()
                .setPayloadWorker(gliaPayloadProcessor)
                .setName(SERVICE_NAME)
                .setZookeeperHosts(ZOOKEEPER_CONNECTION_STRING)
                .setServiceBasePath(BASE_PATH)
                .setAutoSelectPort(true)
                .setKeepClientAlive(false)
                .build();

        discoverer.advertise(new ServerMetadataBuilder().build(serverOne), BASE_PATH);
        discoverer.advertise(new ServerMetadataBuilder().build(serverTwo), BASE_PATH);

        Thread.sleep(1000);

        List<ServerMetadata> metadataList = discoverer.discover(SERVICE_NAME);

        discoverer.close();

        serverOne.shutdown();
        serverTwo.shutdown();

    }

}
