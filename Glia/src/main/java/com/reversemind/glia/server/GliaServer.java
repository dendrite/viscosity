package com.reversemind.glia.server;

import com.reversemind.glia.GliaPayload;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
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

        this.name = UUID.randomUUID().toString() + "__" + this.port;
        this.dropClientConnection = dropClientConnection;
        this.gliaPayloadWorker = gliaPayloadWorker;

        System.out.println("\n\n\n GliaServer [" + this.name + "] started on port:" + this.port + "\n\n\n");
    }

    /**
     *
     * @param port
     * @param gliaPayloadWorker
     * @param dropClientConnection
     */
    public GliaServer(int port, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        this.port = port;
        this.dropClientConnection = dropClientConnection;
        this.gliaPayloadWorker = gliaPayloadWorker;
        this.name = UUID.randomUUID().toString() + "__" + port;
        System.out.println("\n\n\n GliaServer [" + this.name + "] started on port:" + this.port + "\n\n\n");
    }

    public GliaServer(String serverName, int port, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        this.port = port;
        this.dropClientConnection = dropClientConnection;
        this.gliaPayloadWorker = gliaPayloadWorker;
        this.name = serverName + "_port:" + port;
        System.out.println("\n\n\n GliaServer [" + this.name + "] started on port:" + this.port + "\n\n\n");
    }

    /**
     * Get server name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
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
     *
     */
    public void run() {
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the pipeline factory.
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        new ObjectEncoder(),
                        new ObjectDecoder(
                                ClassResolvers.cacheDisabled(getClass().getClassLoader())),
                        new GliaServerHandler(gliaPayloadWorker, dropClientConnection));
            }
        });

        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(port));

        System.out.println("Server started!");
    }

    public static void main(String[] args) throws Exception {
        int port = 7000;
        //new GliaServer(port.run();
    }
}
