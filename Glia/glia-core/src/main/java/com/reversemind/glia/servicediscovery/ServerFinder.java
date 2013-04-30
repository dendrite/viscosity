package com.reversemind.glia.servicediscovery;

import com.google.common.base.Throwables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.utils.ZKPaths;
import com.netflix.curator.x.discovery.JsonServiceInstance;
import com.netflix.curator.x.discovery.ServiceDiscovery;
import com.netflix.curator.x.discovery.ServiceDiscoveryBuilder;
import com.netflix.curator.x.discovery.ServiceInstance;
import com.reversemind.glia.servicediscovery.serializer.InstanceSerializerFactory;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Date: 4/30/13
 * Time: 9:18 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ServerFinder implements Serializable {

    private static Logger LOG = Logger.getLogger(ServerFinder.class);

    private ServiceDiscovery<ServerMetadata> discovery;
    private CuratorFramework curatorFramework;
    private String basePath;

    /**
     *
     * @param curatorFramework
     * @param instanceSerializerFactory
     */
    public ServerFinder(CuratorFramework curatorFramework,
                        InstanceSerializerFactory instanceSerializerFactory,
                        String basePath) {

        discovery = ServiceDiscoveryBuilder.builder(ServerMetadata.class)
                .basePath(basePath)
                .client(curatorFramework)
                .serializer(instanceSerializerFactory
                        .getInstanceSerializer(new TypeReference<JsonServiceInstance<ServerMetadata>>() {
                        }))
                .build();

        this.curatorFramework = curatorFramework;
        this.basePath = basePath;
        try {
            discovery.start();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    /**
     *
     * @param serviceName
     * @return
     */
    public Collection<ServiceInstance<ServerMetadata>> getServers(String serviceName) {

        Collection<ServiceInstance<ServerMetadata>> instances;
        try {
            instances = discovery.queryForInstances(serviceName);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        return instances;
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    private void p(String name) throws Exception {
        String path = ZKPaths.makePath(ZKPaths.makePath(this.basePath, name), null);
        List<String> files = curatorFramework.getChildren().forPath("/baloo/services");
        LOG.debug(files);
    }

}