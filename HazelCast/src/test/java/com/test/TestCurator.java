package com.test;

import com.google.common.io.Closeables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.state.ConnectionState;
import com.netflix.curator.framework.state.ConnectionStateListener;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.netflix.curator.retry.RetryOneTime;
import com.netflix.curator.test.Timing;
import com.netflix.curator.utils.DebugUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class TestCurator {

    @Test
    public void testZookeeper() throws Exception {
        System.setProperty(DebugUtils.PROPERTY_DONT_LOG_CONNECTION_ISSUES, "true");

        // these are reasonable arguments for the ExponentialBackoffRetry. The first
        // retry will wait 1 second - the second will wait up to 2 seconds - the
        // third will wait up to 4 seconds.
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3); // new RetryOneTime(1)

        int connectionTimeoutMs = new Timing().connection();
        int sessionTimeoutMs = new Timing().session();

        String host = "localhost";
        int port = 2181;
        String connectionString = host + ":" + port;

        Timing timing = new Timing();
        System.out.println("timing.session() - " + timing.session());
        System.out.println("timing.connection() - " + timing.connection());

        // CuratorFrameworkFactory.newClient(host + ":" + port, timing.session(), timing.connection(), new RetryOneTime(1));

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();

        client.start();


        String PATH = "/something";
        client.create().forPath(PATH, "STRING".getBytes());

        System.out.println(client.checkExists().forPath(PATH) == null ? "NOT exist" : "EXIST");
        System.out.println(client.checkExists().forPath(PATH + "/new") == null ? "NOT exist" : "EXIST");

        System.out.println(client.checkExists().forPath(PATH).getDataLength());
        System.out.println(client.checkExists().forPath(PATH));


        System.out.println(new String(client.getData().forPath(PATH).clone()));


        client.create().forPath("/yet/one/two/three");


        client.delete().guaranteed().forPath(PATH);

        Closeables.closeQuietly(client);
    }
}
