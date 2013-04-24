package ru.ttk.glia.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
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

    private static final Logger logger = Logger.getLogger(GliaClient.class.getName());

    private final String host;
    private final int port;
    private final int firstMessageSize;
    private ClientBootstrap clientBootstrap;

    public GliaClient(String host, int port, int firstMessageSize) {
        this.host = host;
        this.port = port;
        this.firstMessageSize = firstMessageSize;
    }

    public String listener(){
        System.out.println("Some words");
//        if(this.clientBootstrap != null){
//            this.clientBootstrap.shutdown();
//
//
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//
//            this.clientBootstrap.connect(new InetSocketAddress("localhost", 7000));
//        }
        return "yet another words";
    }

    public void run() throws InterruptedException {

        // Configure the client.
        this.clientBootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the pipeline factory.
        this.clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        new ObjectEncoder(),
                        new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader()))
                        ,
                        new SimpleChannelUpstreamHandler() {

                            private final List<Integer> firstMessage;
                            private final AtomicLong transferredMessages = new AtomicLong();

                            {
                                if (firstMessageSize <= 0) {
                                    throw new IllegalArgumentException(
                                            "firstMessageSize: " + firstMessageSize);
                                }
                                firstMessage = new ArrayList<Integer>(firstMessageSize);
                                for (int i = 0; i < firstMessageSize; i++) {
                                    firstMessage.add(i);
                                }
                            }

                            @Override
                            public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
                                if (e instanceof ChannelStateEvent &&
                                        ((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
                                    logger.info(e.toString());
                                }
                                super.handleUpstream(ctx, e);
                            }

                            @Override
                            public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
                                // Send the first message if this handler is a client-side handler.
                                e.getChannel().write(firstMessage);
                            }

                            @Override
                            public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
                                // Echo back the received object to the server.
                                transferredMessages.incrementAndGet();
                                listener();
                                //e.getChannel().write(e.getMessage());
                                e.getChannel().close();
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
                                logger.log(
                                        Level.WARNING,
                                        "Unexpected exception from downstream.",
                                        e.getCause());
                                e.getChannel().close();
                            }
                        }
                        //new ObjectEchoClientHandler(firstMessageSize)
                );
            }
        });

        // Start the connection attempt.
        ChannelFuture channelFuture = this.clientBootstrap.connect(new InetSocketAddress(host, port));

//        channelFuture.awaitUninterruptibly();
//        if (!channelFuture.isSuccess()) {
//            channelFuture.getCause().printStackTrace();
//        }
//
//        channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();

//        channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
//        factory.releaseExternalResources();


        channelFuture.getChannel().getCloseFuture().await(3000);
        if (!channelFuture.isSuccess()) {
            channelFuture.getCause().printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int port = 7000;
        final int firstMessageSize = 100;

        GliaClient gliaClient = new GliaClient(host, port, firstMessageSize);
        gliaClient.run();
    }
}
