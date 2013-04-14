package ru.ttk.netty.echo.client;

import com.esotericsoftware.kryo.Kryo;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import ru.ttk.netty.echo.kryo.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 *
 */
public class KryoClient {

    public static void main(String[] argv) throws InterruptedException {

        String host = "localhost";
        int port = 7777;






        // Configure the client.
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the pipeline factory.
//        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
//            public ChannelPipeline getPipeline() throws Exception {
//                return Channels.pipeline(
//                        new KryoClientHandler(kryoMessage, new KryoValueSerializer(kryo), new KryoValueDeserializer(kryo)));
//            }
//        });


        bootstrap.setPipelineFactory(new KryoClientPipelineFactory("__CLIENT__"));


        // Start the connection attempt.
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        // Wait until the connection is closed or the connection attempt fails.
        //future.getChannel().getCloseFuture().await(10000L);// .awaitUninterruptibly();
        future.getChannel().getCloseFuture().awaitUninterruptibly();


        // Shut down thread pools to exit.
        bootstrap.releaseExternalResources();
    }

}
