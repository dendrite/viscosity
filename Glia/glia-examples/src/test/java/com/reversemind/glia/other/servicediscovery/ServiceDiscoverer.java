package com.reversemind.glia.other.servicediscovery;

import com.google.common.base.Throwables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.RetryNTimes;
import com.netflix.curator.utils.EnsurePath;
import com.netflix.curator.x.discovery.ServiceInstance;
import com.reversemind.glia.other.servicediscovery.serializer.InstanceSerializerFactory;
import com.reversemind.glia.other.servicediscovery.serializer.ServerMetadata;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 8:43 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ServiceDiscoverer implements Serializable, Closeable {

    private static Logger LOG = Logger.getLogger(ServiceDiscoverer.class);

    public static final String ZOOKEEPER_CONNECTION_STRING = "127.0.0.1:2181";
    public static final String BASE_PATH = "/baloo/services";

    private final CuratorFramework curatorFramework;


    public ServiceDiscoverer() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectionTimeoutMs(1000)
                .retryPolicy(new RetryNTimes(10, 500))
                .connectString(ZOOKEEPER_CONNECTION_STRING)
                .build();
        curatorFramework.start();
    }

    public void discover() {
        try {
            new EnsurePath(BASE_PATH).ensure(curatorFramework.getZookeeperClient());
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }

        ServerFinder serverFinder = new ServerFinder(curatorFramework, this.getInstanceSerializerFactory());

        for (ServiceInstance<ServerMetadata> instance : serverFinder.getServers("GLIA_SERVER")) {
            ServerMetadata serverMetadata = instance.getPayload();
            System.out.println("found a server with parameters:" + serverMetadata);
        }
    }

    public void advertise(ServerMetadata serverMetadata) {
        ServerAdvertiser serverAdvertiser = new ServerAdvertiser(curatorFramework, getInstanceSerializerFactory(), serverMetadata);
        serverAdvertiser.advertiseAvailability();
        System.out.println("advertised...");
    }

    private InstanceSerializerFactory getInstanceSerializerFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new InstanceSerializerFactory(objectMapper.reader(), objectMapper.writer());
    }

    @Override
    public void close() throws IOException {
    }

    public static void main(String... args) throws Exception {
        ServiceDiscoverer sd = new ServiceDiscoverer();

        ServerMetadata serverMetadata = new ServerMetadata(
                "GLIA_SERVER",
                "INSTANCE 001",
                "localhost",
                7000
        );

        sd.advertise(serverMetadata);

        sd.advertise(new ServerMetadata(
                "GLIA_SERVER",
                "INSTANCE 002",
                "localhost",
                7001
        ));

        Thread.sleep(1000);

        sd.discover();

        sd.close();
    }
}
