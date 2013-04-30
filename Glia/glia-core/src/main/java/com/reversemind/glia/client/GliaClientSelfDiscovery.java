package com.reversemind.glia.client;

import com.reversemind.glia.servicediscovery.ServiceDiscoverer;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 2:01 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaClientSelfDiscovery extends GliaClient implements Serializable {

    private String zookeeperConnectionString;
    private String serviceBasePath;
    private String serviceName;
    private IServerSelectorStrategy serverSelectorStrategy;

    private ServiceDiscoverer serviceDiscoverer;

    /**
     *
     * @param zookeeperConnectionString
     * @param serviceBasePath
     * @param serviceName
     * @param serverSelectorStrategy
     */
    public GliaClientSelfDiscovery(String zookeeperConnectionString,
                                   String serviceBasePath,
                                   String serviceName,
                                   IServerSelectorStrategy serverSelectorStrategy) {
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.serviceName = serviceName;
        this.serverSelectorStrategy = serverSelectorStrategy;
        this.serviceDiscoverer = new ServiceDiscoverer(this.zookeeperConnectionString, this.serviceBasePath);
    }

    @Override
    public void run() throws Exception {
        ServerMetadata serverMetadata = this.serverSelectorStrategy.selectServer(this.serviceDiscoverer.discover(this.serviceName));
        if(serverMetadata != null && !StringUtils.isEmpty(serverMetadata.getHost()) && serverMetadata.getPort() > 0){
            System.out.println("found server:" + serverMetadata);
            this.port = serverMetadata.getPort();
            this.host = serverMetadata.getHost();
            super.run();
        }
        throw new Exception("Could not find any available server for the ServiceName:" + this.serviceName + " on path:" + this.serviceBasePath);
    }

    @Override
    public void shutdown(){
        super.shutdown();
        if(this.serviceDiscoverer != null){
            try {
                this.serviceDiscoverer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
