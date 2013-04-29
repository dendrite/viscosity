package com.reversemind.glia.zookeeper.curator;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

/**
 *
 */
public class TestZookeeperCurator {

    @Test
    public void testZookeeperCurator() throws Exception {

//        String connectionString = "127.0.0.1:2128";
//
//        CuratorFramework client = null;
//
//            client = CuratorFrameworkFactory.builder()
//                    .connectionTimeoutMs(1000)
//                    .retryPolicy(new RetryNTimes(10, 500))
//                    .connectString(connectionString)
//                    .build();
//
//        client.start();

        // these are reasonable arguments for the ExponentialBackoffRetry. The first
        // retry will wait 1 second - the second will wait up to 2 seconds - the
        // third will wait up to 4 seconds.

        String connectionString = "localhost:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectionString, new ExponentialBackoffRetry(500, 3));
        client.start();

        System.out.println(client.getNamespace());

        //String path = "/zookeeper/testNode_EPH_" + System.currentTimeMillis();
        String path = "/testNode_EPH_" + System.currentTimeMillis();
        String value = "simple data";

        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, value.getBytes());

        byte[] some = client.getData().forPath(path);
        System.out.println("Get back is:" + new String(some));

        // Let's check that EPHEMERAL will be deleted at the end of client session
        Thread.sleep(2000);
        client.close();
    }
}
