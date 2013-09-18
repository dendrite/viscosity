package com.reversemind.glia.servicediscovery;

import com.google.common.base.Throwables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.x.discovery.JsonServiceInstance;
import com.netflix.curator.x.discovery.ServiceDiscovery;
import com.netflix.curator.x.discovery.ServiceDiscoveryBuilder;
import com.netflix.curator.x.discovery.ServiceInstance;
import com.netflix.curator.x.discovery.details.InstanceSerializer;
import com.reversemind.glia.servicediscovery.serializer.InstanceSerializerFactory;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 8:46 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ServerAdvertiser implements Serializable {

    private static Logger LOG = Logger.getLogger(ServerAdvertiser.class);

    private CuratorFramework curatorFramework;
    private InstanceSerializer<ServerMetadata> jacksonInstanceSerializer;
    private ServerMetadata serverMetadata;
    private String basePath;

    public ServerAdvertiser(CuratorFramework curatorFramework,
                            InstanceSerializerFactory instanceSerializerFactory,
                            ServerMetadata serverMetadata,
                            String basePath) {

        this.curatorFramework = curatorFramework;
        this.jacksonInstanceSerializer = instanceSerializerFactory.getInstanceSerializer(
                new TypeReference<JsonServiceInstance<ServerMetadata>>() {
                }
        );
        this.serverMetadata = serverMetadata;
        this.basePath = basePath;
    }

    public void advertiseAvailability() {
        try {
            ServiceDiscovery<ServerMetadata> discovery = this.getDiscovery();
            discovery.start();

            ServiceInstance serviceInstance = this.getInstance();
//            LOG.info("Service:" + serviceInstance + " is available");
            discovery.registerService(serviceInstance);

            // TODO ??????
            //discovery.close();
        } catch (Exception ex) {
            // look through it again
            ex.printStackTrace();
            throw Throwables.propagate(ex);
        }
    }

    /**
     * @return
     * @throws Exception
     */
    private ServiceInstance<ServerMetadata> getInstance() throws Exception {

        ServerMetadata metadata = new ServerMetadata(
                this.serverMetadata.getName(),
                this.serverMetadata.getInstance(),
                this.serverMetadata.getHost(),
                this.serverMetadata.getPort(),
                this.serverMetadata.getMetrics()
        );

        return ServiceInstance.<ServerMetadata>builder()
                .name(this.serverMetadata.getName())
                .address(this.serverMetadata.getHost())
                .port(this.serverMetadata.getPort())
                .id(this.serverMetadata.getInstance())
                .payload(metadata)
                .build();
    }

    private ServiceDiscovery<ServerMetadata> getDiscovery() {
        return ServiceDiscoveryBuilder.builder(ServerMetadata.class)
                .basePath(this.basePath)
                .client(curatorFramework)
                .serializer(jacksonInstanceSerializer)
                .build();
    }

    public void deAdvertiseAvailability() {
        try {
            ServiceDiscovery<ServerMetadata> discovery = this.getDiscovery();
            discovery.start();
            discovery.unregisterService(getInstance());
            //discovery.close();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public void close() throws IOException {
        getDiscovery().close();
    }

}
