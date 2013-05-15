package com.reversemind.glia.server;

import com.reversemind.glia.servicediscovery.ServiceDiscoverer;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;
import org.apache.commons.lang3.StringUtils;

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
public class GliaServerAdvertiser extends GliaServer implements Serializable {

    private String zookeeperConnectionString;
    private String serviceBasePath;
    private ServiceDiscoverer serviceDiscoverer;
    private ServerMetadata metadata;

    private MetricsUpdateTask metricsUpdateTask;
    private Timer timer;

    /**
     *
     * @param builder
     */
    public GliaServerAdvertiser(GliaServerFactory.Builder builder){
        super(builder);

        if(StringUtils.isEmpty(builder.getZookeeperConnectionString())){
            throw new RuntimeException("Need zookeeper connection string");
        }
        this.zookeeperConnectionString = builder.getZookeeperConnectionString();

        if(StringUtils.isEmpty(builder.getServiceBasePath())){
            throw new RuntimeException("Need zookeeper base path string");
        }
        this.serviceBasePath = builder.getServiceBasePath();

        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                this.getHost(),
                this.getPort(),
                this.metrics
        );
    }

    /**
     *
     */
    @Override
    public void start() {
        super.start();
        this.serviceDiscoverer = new ServiceDiscoverer(this.zookeeperConnectionString, this.serviceBasePath);
        this.serviceDiscoverer.advertise(this.metadata, this.serviceBasePath);
    }

    /**
     * Update different metrics
     */
    public void updateMetrics(){
        this.metadata = new ServerMetadata(
                this.getName(),
                this.getInstanceName(),
                this.getHost(),
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
