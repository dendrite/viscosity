package com.reversemind.glia.server;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.RetryNTimes;
import com.reversemind.glia.servicediscovery.ServiceDiscoverer;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;

import java.io.IOException;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

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

    private MetricsUpdateTask metricsUpdateTask;
    private Timer timer;

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    IGliaPayloadProcessor gliaPayloadWorker,
                                    boolean dropClientConnection) {
        super(gliaPayloadWorker, dropClientConnection);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort(),
                this.metrics
        );

    }

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    int port,
                                    IGliaPayloadProcessor gliaPayloadWorker,
                                    boolean dropClientConnection) {
        super(port, gliaPayloadWorker, dropClientConnection);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort(),
                this.metrics
        );
    }

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    String serverName,
                                    int port,
                                    IGliaPayloadProcessor gliaPayloadWorker,
                                    boolean dropClientConnection) {
        super(serverName, port, gliaPayloadWorker, dropClientConnection);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort(),
                this.metrics
        );
    }

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    String serverName,
                                    int port,
                                    IGliaPayloadProcessor gliaPayloadWorker,
                                    boolean dropClientConnection,
                                    String instanceName) {
        super(serverName, port, gliaPayloadWorker, dropClientConnection, instanceName);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort(),
                this.metrics
        );
    }

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    String serverName,
                                    IGliaPayloadProcessor gliaPayloadWorker,
                                    boolean dropClientConnection) {
        super(serverName, gliaPayloadWorker, dropClientConnection);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort(),
                this.metrics
        );
    }

    public GliaServerSelfAdvertiser(String zookeeperConnectionString,
                                    String serviceBasePath,
                                    String serverName,
                                    IGliaPayloadProcessor gliaPayloadWorker,
                                    boolean dropClientConnection,
                                    String instanceName) {
        super(serverName, gliaPayloadWorker, dropClientConnection, instanceName);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort(),
                this.metrics
        );

        this.metricsUpdateTask = new MetricsUpdateTask();
        this.timer = new Timer();
        this.timer.schedule(this.metricsUpdateTask, 1000, 1000);

    }

    public GliaServerSelfAdvertiser(String serverName, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection, String instanceName) {
        super(serverName, gliaPayloadWorker, dropClientConnection, instanceName);
        this.zookeeperConnectionString = zookeeperConnectionString;
        this.serviceBasePath = serviceBasePath;
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort(),
                this.metrics
        );
    }

    /**
     *
     */
    @Override
    public void run() {
        super.run();
        this.serviceDiscoverer = new ServiceDiscoverer(this.zookeeperConnectionString, this.serviceBasePath);
        this.serviceDiscoverer.advertise(this.metadata, this.serviceBasePath);
    }

    /**
     *
     */
    public void updateMetrics(){
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                "localhost",
                this.getPort(),
                this.metrics
        );
        this.serviceDiscoverer.advertise(this.metadata, this.serviceBasePath);
    }

    /**
     *
     */
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

    /**
     *
     */
    private class MetricsUpdateTask extends TimerTask{
        @Override
        public void run() {
            updateMetrics();
        }
    }
}
