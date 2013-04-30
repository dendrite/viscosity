package com.reversemind.glia.other.servicediscovery;

import com.google.common.base.Throwables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.x.discovery.JsonServiceInstance;
import com.netflix.curator.x.discovery.ServiceDiscovery;
import com.netflix.curator.x.discovery.ServiceDiscoveryBuilder;
import com.netflix.curator.x.discovery.ServiceInstance;
import com.netflix.curator.x.discovery.details.InstanceSerializer;
import com.reversemind.glia.other.servicediscovery.serializer.InstanceSerializerFactory;
import com.reversemind.glia.other.servicediscovery.serializer.ServerMetadata;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonProperty;
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

    private final CuratorFramework curatorFramework;
    private final InstanceSerializer<ServerMetadata> jacksonInstanceSerializer;
    private final ServerMetadata serverMetadata;

    public ServerAdvertiser(CuratorFramework curatorFramework, InstanceSerializerFactory instanceSerializerFactory, ServerMetadata serverMetadata) {

        this.curatorFramework = curatorFramework;
        this.jacksonInstanceSerializer = instanceSerializerFactory.getInstanceSerializer(
                new TypeReference<JsonServiceInstance<ServerMetadata>>() {
                }
        );
        this.serverMetadata = serverMetadata;
    }

    public void advertiseAvailability() {
        try {
            ServiceDiscovery<ServerMetadata> discovery = this.getDiscovery();
            discovery.start();

            ServiceInstance serviceInstance = getInstance();
            LOG.info("Service:" + serviceInstance + " is available");
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
                this.serverMetadata.getPort()
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
                .basePath(ServiceDiscoverer.BASE_PATH)
                .client(curatorFramework)
                .serializer(jacksonInstanceSerializer)
                .build();
    }

    public void close() throws IOException {
        getDiscovery().close();
    }

}
