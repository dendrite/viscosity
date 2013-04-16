package ru.ttk.zookeeper;

import com.google.common.base.Throwables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.netflix.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.io.Serializable;

/**
 * Date: 4/16/13
 * Time: 1:10 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class TestNetflixCurator implements Serializable {

    public static void main(String... args) throws Exception {

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

        String path = "/zookeeper/testNode_EPH_" + System.currentTimeMillis();
        String value = "simple data";

        //client.create().forPath(path, value.getBytes());
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, value.getBytes());



        byte[] some = client.getData().forPath(path);
        System.out.println("Get back is:" + new String(some));


        Thread.sleep(30000);
        client.close();

    }

}
