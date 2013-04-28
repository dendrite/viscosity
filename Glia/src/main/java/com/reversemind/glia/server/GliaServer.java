package com.reversemind.glia.server;

import com.reversemind.glia.GliaPayload;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import sun.util.LocaleServiceProviderPool;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * Date: 4/24/13
 * Time: 10:07 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaServer implements Serializable {

    private String name;
    private String instanceName;

    private ServerBootstrap serverBootstrap;
    private SimpleChannelUpstreamHandler handler;
    private Metrics metrics;

    private final int port;
    private GliaPayload gliaPayload;
    private boolean dropClientConnection = false;
    private IGliaPayloadProcessor gliaPayloadWorker;

    /**
     * Autodiscover an available port and start GliaServer
     *
     * @param gliaPayloadWorker
     * @param dropClientConnection
     */
    public GliaServer(IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {

        try {
            ServerSocket serverSocket = new ServerSocket(0);
            if (serverSocket.getLocalPort() == -1) {
                System.exit(-100);
                throw new RuntimeException("\n\nCould not start GliaServer 'cause no any available free port in system");
            }

            this.port = serverSocket.getLocalPort();

            serverSocket.close();
            int count = 0;
            while (!serverSocket.isClosed()) {
                if (count++ > 10) {
                    throw new RuntimeException("Could not start GliaServer");
                }
                try {
                    Thread.sleep(100);
                    System.out.println("Waiting for closing autodiscovered socket try number#" + count);
                } catch (InterruptedException e) {
                    System.exit(-100);
                    throw new RuntimeException("Could not start GliaServer");
                }
            }
            serverSocket = null;
        } catch (Exception e) {
            throw new RuntimeException("\n\nCould not start GliaServer 'cause no any available free port in system");
        }

        this.metrics = new Metrics();
        this.name = UUID.randomUUID().toString();
        this.dropClientConnection = dropClientConnection;
        this.gliaPayloadWorker = gliaPayloadWorker;
        this.instanceName = UUID.randomUUID().toString();
        System.out.println("\n\n\n" +
                " GliaServer started " +
                "\n-------------------" +
                "\n name:" + this.name +
                "\n instance:" + this.instanceName +
                "\n port:" + this.port +
                "  \n\n\n");
    }

    /**
     *
     * @param port
     * @param gliaPayloadWorker
     * @param dropClientConnection
     */
    public GliaServer(int port, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        this.metrics = new Metrics();
        this.port = port;
        this.dropClientConnection = dropClientConnection;
        this.gliaPayloadWorker = gliaPayloadWorker;
        this.name = UUID.randomUUID().toString();
        this.instanceName = UUID.randomUUID().toString();
        System.out.println("\n\n\n" +
                " GliaServer started " +
                "\n-------------------" +
                "\n name:" + this.name +
                "\n instance:" + this.instanceName +
                "\n port:" + this.port +
                "  \n\n\n");
    }

    /**
     *
     * @param serverName
     * @param port
     * @param gliaPayloadWorker    -
     * @param dropClientConnection - disconnect a client after response
     */
    public GliaServer(String serverName, int port, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        this.metrics = new Metrics();
        this.port = port;
        this.dropClientConnection = dropClientConnection;
        this.gliaPayloadWorker = gliaPayloadWorker;
        this.name = serverName;
        this.instanceName = UUID.randomUUID().toString();
        System.out.println("\n\n\n" +
                " GliaServer started " +
                "\n-------------------" +
                "\n name:" + this.name +
                "\n instance:" + this.instanceName +
                "\n port:" + this.port +
                "  \n\n\n");
    }

    /**
     * Get server name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Instance name each time is a UUID
     *
     * @return
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * Get port number of GliaServer
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * Get server metrics
     *
     * @return
     */
    public Metrics getMetrics() {
        return this.metrics;
    }

    /**
     *
     */
    public void run() {
        // Configure the server.
        this.serverBootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        this.handler = new GliaServerHandler(gliaPayloadWorker, metrics, dropClientConnection);

        // Set up the pipeline factory.
        this.serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        new ObjectEncoder(),
                        new ObjectDecoder(
                                ClassResolvers.cacheDisabled(getClass().getClassLoader())),
                        handler);
            }
        });

        // Bind and start to accept incoming connections.
        this.serverBootstrap.bind(new InetSocketAddress(port));

        System.out.println("Server started!");
    }

    /**
     * Shutdown server
     */
    public void shutdown() {
        if (this.serverBootstrap != null) {
            this.serverBootstrap.releaseExternalResources();
            this.serverBootstrap.shutdown();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 7000;
        //new GliaServer(port.run();
    }
}
