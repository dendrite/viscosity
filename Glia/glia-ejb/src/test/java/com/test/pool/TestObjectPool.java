package com.test.pool;

import com.reversemind.glia.client.GliaClientServerDiscovery;
import com.reversemind.glia.client.IGliaClient;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 *
 */
public class TestObjectPool {

    public static void main(String... args) throws Exception {
        ClientFactory clientFactory = new ClientFactory("META-INF/glia-client-context.xml","gliaClientServerDiscovery", GliaClientServerDiscovery.class);
        GenericObjectPool<IGliaClient> pool = new GenericObjectPool<IGliaClient>(clientFactory, 5);

        IGliaClient gliaClient = pool.borrowObject();
    }

}
