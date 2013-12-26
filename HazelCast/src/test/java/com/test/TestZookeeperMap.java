package com.test;

import com.google.common.io.Closeables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.netflix.curator.test.Timing;
import com.netflix.curator.utils.DebugUtils;
import net.killa.kept.KeptMap;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class TestZookeeperMap {

    static CuratorFramework client;

    public void save(CuratorFramework curatorFramework, String parentPath, String name, Serializable object) throws Exception {

        byte[] data = SerializationUtils.serialize(object);

        if(curatorFramework.checkExists().forPath(parentPath) == null){
            curatorFramework.create().forPath(parentPath);
        }

        if(curatorFramework.checkExists().forPath(parentPath + "/" + name) == null){
            curatorFramework.create().forPath(parentPath + "/" + name, data);
        }else{
            curatorFramework.setData().forPath(parentPath + "/" + name, data);
        }

    }

    public Object read(CuratorFramework curatorFramework, String parentPath, String name) throws Exception {
        if(curatorFramework.checkExists().forPath(parentPath + "/name") == null){
            throw new IOException("Path:" + parentPath + "/" + name + " is not exist");
        }
        byte[] data = curatorFramework.getData().forPath(parentPath + "/" + name);
        return SerializationUtils.deserialize(data);
    }


    @Test
    public void testReadWrite(){

    }

    @Test
    public void testObjectSave() throws Exception {

        List<OtherSimpleDTO> list = new ArrayList<OtherSimpleDTO>();
        for (int i = 0; i < 10000; i++) {
            list.add(new OtherSimpleDTO("n_" + i, "v_" + i));
        }

        // public SimpleDTO(Long id, String name, Object someValue, List<OtherSimpleDTO> list) {
        SimpleDTO simpleDTO = new SimpleDTO(1L, "name#1", null, list);


        byte[] data = SerializationUtils.serialize(simpleDTO);
        System.out.println("DATA:" + data.length);


        String PATH = "/example";
        if(client.checkExists().forPath(PATH) == null){
            client.create().forPath(PATH);
        }

        client.create().forPath(PATH + "/" + new Date().getTime(), data);

    }




    @Test
    public void testReadObjectFromZooKeeper() throws Exception {
        String PATH = "/example/" + "1388048916015";

        byte[] data = client.getData().forPath(PATH);
        System.out.println( "----------------" + data.length );

        SimpleDTO simpleDTO = (SimpleDTO) SerializationUtils.deserialize(data);

        System.out.println(simpleDTO.getId());
        System.out.println(simpleDTO.getName());
        System.out.println(simpleDTO.getSomeValue());
        System.out.println(simpleDTO.getList().size());

    }

    @Test
    public void testKeptMap() throws Exception {

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
