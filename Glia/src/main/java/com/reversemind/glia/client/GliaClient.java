package com.reversemind.glia.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import com.reversemind.glia.GliaPayload;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 4/24/13
 * Time: 10:08 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaClient implements Serializable {

    private static final Logger LOG = Logger.getLogger(GliaClient.class.getName());

    private GliaPayload gliaPayload;
    private boolean received = false;

    private final String host;
    private final int port;

    private ClientBootstrap clientBootstrap;
    private Channel channel;
    private ChannelFuture channelFuture;
    private ChannelFactory channelFactory;

    private boolean running = false;

    public GliaClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.gliaPayload = null;
    }

    public boolean isRunning() {
        return running;
    }

    public GliaPayload getGliaPayload() {
        return this.gliaPayload;
    }

    private void serverListener(Object object) {
        if(object instanceof GliaPayload){
            LOG.info("Get from server:" + ((GliaPayload) object).toString());
            this.gliaPayload = ((GliaPayload) object);
            return;
        }
        System.out.println("Object is not a GliaPayload");
    }

    /**
     * Send to server
     *
     * @param gliaPayloadSend
     * @throws IOException
     */
    public void send(GliaPayload gliaPayloadSend) throws IOException {
        this.gliaPayload = null;
        if(this.channel != null && this.channel.isConnected()){
            LOG.info("Connected:" + this.channel.isConnected());

            if(gliaPayloadSend != null){
                LOG.info("Send from gliaPayload:" + gliaPayloadSend.toString());
                gliaPayloadSend.setClientTimestamp(System.currentTimeMillis());
                this.channel.write(gliaPayloadSend);

                return;
            }
        }
        throw new IOException("Channel is closed");
    }

    /**
     *
     */
    public void shutdown(){
        this.channelFuture.getChannel().close();
        this.channelFactory.releaseExternalResources();

        this.clientBootstrap.releaseExternalResources();
        this.clientBootstrap.shutdown();

        this.running = false;
    }

    /**
     *
     * @throws Exception
     */
    public void run() throws Exception {

        if(this.running){
            throw new InstantiationException("Glia client is running");
        }

        // Configure the client.
        // TODO make it more robust & speedy
        this.channelFactory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());

        this.clientBootstrap = new ClientBootstrap(channelFactory);

        ChannelPipelineFactory channelPipelineFactory = new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        new ObjectEncoder(),
                        new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader()))
                        ,
                        new SimpleChannelUpstreamHandler(){

                            @Override
                            public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
                                if (e instanceof ChannelStateEvent &&
                                        ((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
                                    LOG.info(e.toString());
                                }
                                super.handleUpstream(ctx, e);
                            }

                            @Override
                            public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent event) {
                                channel = event.getChannel();
                                // Send the first message
                                // channel.write(firstMessage);
                            }

                            @Override
                            public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
                                // Get
                                serverListener(e.getMessage());
                                ctx.sendUpstream(e);

                                //e.getChannel().write(e.getStatus());
                                //e.getChannel().close();
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
                                LOG.log(
                                        Level.WARNING,
                                        "Unexpected exception from downstream.",
                                        e.getCause());
                                e.getChannel().close();
                            }
                        }
                );
            }
        };


        // Set up the pipeline factory.
        this.clientBootstrap.setPipelineFactory(channelPipelineFactory);

        // Start the connection attempt.
        // ChannelFuture channelFuture = this.clientBootstrap.connect(new InetSocketAddress(host, port));
        this.channelFuture = this.clientBootstrap.connect(new InetSocketAddress(host, port));


        // INFO

        // correct shut down a client
        // see http://netty.io/3.6/guide/#start.12 - 9.  Shutting Down Your Application

        // http://stackoverflow.com/questions/10911988/shutting-down-netty-server-when-client-connections-are-open
        //    Netty Server Shutdown
        //
        //    Close server channel
        //    Shutdown boss and worker executor
        //    Release server bootstrap resource
        //    Example code
        //
        //    ChannelFuture cf = serverChannel.close();
        //    cf.awaitUninterruptibly();
        //    bossExecutor.shutdown();
        //    workerExecutor.shutdown();
        //    thriftServer.releaseExternalResources();

        // !!! see also - http://massapi.com/class/cl/ClientBootstrap.html
        System.out.println("1");
        // just wait for server connection for 3sec.
        channelFuture.await(3000);
        if (!channelFuture.isSuccess()) {
            channelFuture.getCause().printStackTrace();
            channelFactory.releaseExternalResources();
        }

        // if need disconnect right after server response
        //  channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
        //  channelFactory.releaseExternalResources();

        this.running = true;

    }
}
