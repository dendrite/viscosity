package com.test.echo.server;

import com.esotericsoftware.kryo.Kryo;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import com.test.echo.kryo.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 *
 */
public class KryoServer {

    public static void main(String[] argv){

        int port = 7777;

        final Kryo kryo = new Kryo();
        kryo.register(Long.class);
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(SimpleObject.class);
        kryo.register(SimpleObject2.class);
        kryo.register(KryoMessage.class);

////        kryo.register(TClass1.class, new FieldSerializer(kryo,TClass1.class));
////        kryo.register(TClass2.class, new FieldSerializer(kryo,TClass2.class));
////        kryo.register(Long.class);
////        kryo.register(List.class);
////        kryo.register(ArrayList.class);
////        kryo.register(String.class);
//        //kryo.register(TClass1.class);
//        //kryo.register(TClass2.class);
//
//        FieldSerializer someClassSerializer = new FieldSerializer(kryo, TClass2.class);
//
//        CollectionSerializer listSerializer = new CollectionSerializer();
//        listSerializer.setElementClass(TClass1.class,someClassSerializer);
//        listSerializer.setElementClass(Long.class,new DefaultSerializers.LongSerializer());
//        listSerializer.setElementClass(String.class,new DefaultSerializers.StringSerializer());
//
//        listSerializer.setElementsCanBeNull(false);
//        someClassSerializer.getField("list").setClass(ArrayList.class, listSerializer);
//        kryo.register(TClass2.class, someClassSerializer);




        ServerBootstrap networkServer = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));


//        ExecutorService bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS);
//        ExecutorService ioExec = new OrderedMemoryAwareThreadPoolExecutor(4 /* число рабочих потоков */, 400000000, 2000000000, 60, TimeUnit.SECONDS);
//
//
//        ServerBootstrap networkServer = new ServerBootstrap(
//                new NioServerSocketChannelFactory(
//                        bossExec,
//                        ioExec,
//                        4 /* то же самое число рабочих потоков */
//                )
//        );

        // Configure the server.
//        ServerBootstrap bootstrap = new ServerBootstrap(
//                new NioServerSocketChannelFactory(
//                        Executors.newCachedThreadPool(),
//                        Executors.newCachedThreadPool()));

        // Set up the pipeline factory.
//        networkServer.setPipelineFactory(
//                new ChannelPipelineFactory() {
//                    public ChannelPipeline getPipeline() throws Exception {
//                        return Channels.pipeline(new KryoServerHandler(new KryoValueSerializer(kryo), new KryoValueDeserializer(kryo)));
//                    }
//                }
//        );

        networkServer.setPipelineFactory( new KryoServerPipelineFactory("== SERVER =="));

        // server settings
        networkServer.setOption("keepAlive", true);

        // Bind and start to accept incoming connections.
        networkServer.bind(new InetSocketAddress(port));
    }

}
