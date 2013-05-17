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

    private String zookeeperHosts;
    private String serviceBasePath;
    private ServiceDiscoverer serviceDiscoverer;
    private ServerMetadata metadata;

    private MetricsUpdateTask metricsUpdateTask;
    private Timer timer;

    private boolean useMetrics = false;
    private long periodPublishMetrics = 1000; // ms
    /**
     *
     * @param builder
     */
    public GliaServerAdvertiser(GliaServerFactory.Builder builder){
        super(builder);

        if(StringUtils.isEmpty(builder.getZookeeperHosts())){
            throw new RuntimeException("Need zookeeper connection string");
        }
        this.zookeeperHosts = builder.getZookeeperHosts();

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

        this.useMetrics = builder.isUseMetrics();
        if(builder.isUseMetrics()){

            this.periodPublishMetrics = builder.getPeriodPublishMetrics();

            if(builder.getPeriodPublishMetrics() < 0){
                this.periodPublishMetrics = 1000; //ms
            }

            this.metricsUpdateTask = new MetricsUpdateTask();
            this.timer = new Timer();
            this.timer.schedule(this.metricsUpdateTask, this.periodPublishMetrics, 1000);
        }

    }

    /**
     *
     */
    @Override
    public void start() {
        super.start();
        this.serviceDiscoverer = new ServiceDiscoverer(this.zookeeperHosts, this.serviceBasePath);
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
            synchronized (this.serviceDiscoverer){
                try {
                    this.serviceDiscoverer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
