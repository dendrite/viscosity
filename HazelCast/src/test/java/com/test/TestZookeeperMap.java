package com.test;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.netflix.curator.test.Timing;
import com.netflix.curator.utils.DebugUtils;
import net.killa.kept.KeptMap;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 *
 */
public class TestZookeeperMap {

    @Test
    public void testMap() throws Exception {

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


        String PATH_FOR_MAPS = "/concurrent_maps";

        ZooKeeper keeper = client.getZookeeperClient().getZooKeeper();

        final KeptMap map = new KeptMap(keeper, PATH_FOR_MAPS, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // WHY ONLY Map<String,String> ??

    }
}
