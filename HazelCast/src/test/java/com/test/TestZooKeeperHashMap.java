package com.test;

import com.google.common.io.Closeables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.netflix.curator.test.Timing;
import com.netflix.curator.utils.DebugUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.ttk.hypergate.session.filter.ZooKeeperHashMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class TestZooKeeperHashMap {

    static CuratorFramework client;

    @Test
    public void testPutZooKeeperHashMap(){
        ZooKeeperHashMap zooKeeperHashMap = new ZooKeeperHashMap(client, "/test.path." + new Date().getTime());


        List<OtherSimpleDTO> list = new ArrayList<OtherSimpleDTO>();
        for (int i = 0; i < 10; i++) {
            list.add(new OtherSimpleDTO("n_" + i, "v_" + i));
        }

        // public SimpleDTO(Long id, String name, Object someValue, List<OtherSimpleDTO> list) {
        SimpleDTO simpleDTO = new SimpleDTO(1L, "name#1", null, list);

        zooKeeperHashMap.put("otherSimple", simpleDTO);

    }

    @Test
    public void testGetZooKeeperHashMap(){

        ZooKeeperHashMap zooKeeperHashMap = new ZooKeeperHashMap(client, "/test.path." + new Date().getTime());
        SimpleDTO simpleDTO = (SimpleDTO) zooKeeperHashMap.get("simpleDTO");

        System.out.println(simpleDTO.getId());
        System.out.println(simpleDTO.getName());
        System.out.println(simpleDTO.getSomeValue());
        System.out.println(simpleDTO.getList().size());

    }


    @Test
    public void testChildrenList() throws Exception {
        List<String> list  = client.getChildren().forPath("/zookeeper.map");
        System.out.println("CHILDREN:" + list);

        for(String string: list){
            System.out.println("string:" + string);
            if(string.startsWith("simpleDTO")){
                System.out.println("STARTS with:" + string);
            }
        }
    }

    @After
    public void shutDown(){
        Closeables.closeQuietly(client);
    }

    @Before
    public void createZookeeperClient(){
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

        client = CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();

        client.start();


    }

}
