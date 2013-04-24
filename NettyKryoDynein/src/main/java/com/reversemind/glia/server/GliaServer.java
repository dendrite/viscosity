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

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Date: 4/24/13
 * Time: 10:07 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaServer implements Serializable {

    private final int port;
    private GliaPayload gliaPayload;
    private boolean dropClientConnection = false;
    private IGliaPayloadProcessor gliaPayloadWorker;

    public GliaServer(int port, IGliaPayloadProcessor gliaPayloadWorker, boolean dropClientConnection) {
        this.port = port;
        this.dropClientConnection = dropClientConnection;
        this.gliaPayloadWorker = gliaPayloadWorker;
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
    }

    public static void main(String[] args) throws Exception {
        int port = 7000;
        //new GliaServer(port.run();
    }
}
