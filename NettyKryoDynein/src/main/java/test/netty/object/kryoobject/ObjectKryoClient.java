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
package test.netty.object.kryoobject;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import test.netty.object.ObjectEchoClientHandler;

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

    public ObjectKryoClient(String host, int port, int firstMessageSize) {
        this.host = host;
        this.port = port;
        this.firstMessageSize = firstMessageSize;
    }

    public String listener(){
        System.out.println("Some words");
        if(this.clientBootstrap != null){
            this.clientBootstrap.shutdown();
        }
        return "yet another words";
    }

    public void run() {
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
                                // Send the first message if this handler is a client-side handler.
                                e.getChannel().write(firstMessage);
                            }

                            @Override
                            public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
                                // Echo back the received object to the server.
                                transferredMessages.incrementAndGet();
                                listener();
                                e.getChannel().write(e.getMessage());
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
        this.clientBootstrap.connect(new InetSocketAddress(host, port));

    }

    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int port = 7000;
        final int firstMessageSize = 100;

        ObjectKryoClient objectKryoClient = new ObjectKryoClient(host, port, firstMessageSize);
        objectKryoClient.run();
    }
}
