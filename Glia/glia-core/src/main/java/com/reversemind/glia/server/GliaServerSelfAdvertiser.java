package com.reversemind.glia.server;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.RetryNTimes;
import com.reversemind.glia.servicediscovery.ServiceDiscoverer;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;

import java.io.IOException;
import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 11:14 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaServerSelfAdvertiser extends GliaServer implements Serializable {

    private String zookeeperConnectionString;
    private String serviceBasePath;
    private ServiceDiscoverer serviceDiscoverer;
    private ServerMetadata metadata;

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        super(gliaPayloadWorker, dropClientConnection);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort()
        );

    }

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    int port, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        super(port, gliaPayloadWorker, dropClientConnection);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort()
        );
    }

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    String serverName,
                                    int port, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        super(serverName, port, gliaPayloadWorker, dropClientConnection);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort()
        );
    }

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    String serverName, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        super(serverName, gliaPayloadWorker, dropClientConnection);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort()
        );
    }

    @Override
    public void run() {
        super.run();
        this.serviceDiscoverer = new ServiceDiscoverer(this.zookeeperConnectionString, this.serviceBasePath);
        this.serviceDiscoverer.advertise(this.metadata, this.serviceBasePath);
    }

    @Override
    public void shutdown() {
        super.shutdown();
        if(this.serviceDiscoverer != null){
            try {
                this.serviceDiscoverer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @return
     */
    public ServerMetadata getMetadata() {
        return this.metadata;
    }

}
