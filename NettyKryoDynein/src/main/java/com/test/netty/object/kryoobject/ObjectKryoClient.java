/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.test.netty.object.kryoobject;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class ObjectKryoClient {

    private static final Logger logger = Logger.getLogger(ObjectKryoClient.class.getName());

    private final String host;
    private final int port;
    private final int firstMessageSize;
    private ClientBootstrap clientBootstrap;

    private Channel channel;
    private ChannelFuture channelFuture;
    private ChannelFactory channelFactory;

    public ObjectKryoClient(String host, int port, int firstMessageSize) {
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

    public void sendAgain(){
        if(this.channel != null){

            System.out.println("Connected:" + this.channel.isConnected());

            List<Integer> firstMessage = new ArrayList<Integer>();
            for(int i=0;i<10;i++){
                firstMessage.add(i);
            }
            this.channel.write(firstMessage);
        }
    }


    public void connectAgain(){

        if( this.clientBootstrap != null){
            //.getPipelineFactory().getPipeline().getChannel().disconnect();
            try {
                ChannelPipelineFactory pipelineFactory = this.clientBootstrap.getPipelineFactory();
                pipelineFactory.getPipeline().getChannel().connect(new InetSocketAddress(host, port));
                logger.info("CONNECTED AGAIN");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }


    public void disconnect(){

        if( this.clientBootstrap != null){
            //.getPipelineFactory().getPipeline().getChannel().disconnect();
            try {
                ChannelPipelineFactory pipelineFactory = this.clientBootstrap.getPipelineFactory();

                ChannelPipeline pipeline = pipelineFactory.getPipeline();


                logger.info("DIS-CONNECTED AGAIN");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }


    public void run() throws Exception {
        // Configure the client.


        channelFactory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());

        this.clientBootstrap = new ClientBootstrap(
                channelFactory);


        ChannelPipelineFactory channelPipelineFactory = new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        new ObjectEncoder(),
                        new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader()))
                        ,
                        new SimpleChannelUpstreamHandler(){

                            private final List<Integer> firstMessage;
                            private final AtomicLong transferredMessages = new AtomicLong();

                            {
                                if (firstMessageSize <= 0) {
                                    throw new IllegalArgumentException(
                                            "firstMessageSize: " + firstMessageSize);
                                }
                                firstMessage = new ArrayList<Integer>(firstMessageSize);
                                for (int i = 0; i < firstMessageSize; i ++) {
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
                                channel = e.getChannel();
                                // Send the first message if this handler is a client-side handler.
                                e.getChannel().write(firstMessage);
                            }

                            @Override
                            public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
                                // Echo back the received object to the server.
                                transferredMessages.incrementAndGet();
                                listener();
                                ctx.sendUpstream(e);
                                //e.getChannel().write(e.getMessage());
                                //e.getChannel().close();
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
        };


        // Set up the pipeline factory.
        this.clientBootstrap.setPipelineFactory(channelPipelineFactory);

        // Start the connection attempt.
        //ChannelFuture channelFuture = this.clientBootstrap.connect(new InetSocketAddress(host, port));
        this.channelFuture = this.clientBootstrap.connect(new InetSocketAddress(host, port));


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

        System.out.println("2");
//        channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
//        channelFactory.releaseExternalResources();
        System.out.println("3");


    }

    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int port = 7000;
        final int firstMessageSize = 100;

        ObjectKryoClient objectKryoClient = new ObjectKryoClient(host, port, firstMessageSize);
        System.out.println("4");
        objectKryoClient.run();
        System.out.println("5");

        Thread.sleep(100);
        System.out.println("6");
        objectKryoClient.sendAgain();
        System.out.println("7");
        Thread.sleep(100);
        System.out.println("8");
        objectKryoClient.sendAgain();
        System.out.println("9");

//        Thread.sleep(10000);
//        objectKryoClient.disconnect();
//
//        Thread.sleep(2000);
//        objectKryoClient.connectAgain();


        objectKryoClient.channelFuture.getChannel().close();
        objectKryoClient.channelFactory.releaseExternalResources();

        Thread.sleep(20000);
        objectKryoClient.run();
        objectKryoClient.clientBootstrap.releaseExternalResources();
        objectKryoClient.clientBootstrap.shutdown();
    }
}
