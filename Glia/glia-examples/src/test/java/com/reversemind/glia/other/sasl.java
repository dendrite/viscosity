package com.reversemind.glia.other;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.io.Serializable;

/**
 * Date: 5/21/13
 * Time: 3:10 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class sasl implements Serializable {

    public static void main(String... args) throws Exception {

        // https://issues.apache.org/jira/browse/ZOOKEEPER-1554
        System.setProperty("java.security.auth.login.config", "/opt/zookeeper/conf/jaas.conf");
        System.setProperty("curator-log-events", "true");

        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        CuratorFramework client = builder.connectString("localhost:2181").retryPolicy(new RetryOneTime(1000)).connectionTimeoutMs(10000).sessionTimeoutMs(100000).build();
        client.start();

        Thread.sleep(3000);

        System.out.println("creating the node..");
        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.CREATOR_ALL_ACL).forPath("/node01", "some text".getBytes());
        System.out.println("node created");

        client.close();
    }

}
