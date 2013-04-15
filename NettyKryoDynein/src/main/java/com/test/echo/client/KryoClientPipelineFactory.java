package com.test.echo.client;

import com.esotericsoftware.kryo.Kryo;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import com.test.echo.kryo.*;

import java.util.ArrayList;
import java.util.List;

import static org.jboss.netty.channel.Channels.pipeline;

/**
 *
 */
public class KryoClientPipelineFactory implements ChannelPipelineFactory {


    private KryoValueSerializer kryoValueSerializer;
    private KryoValueDeserializer kryoValueDeserializer;
    private String who_is;

    public KryoClientPipelineFactory(String who_is){
        super();


        this.who_is = who_is;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {

        final Kryo kryo = new Kryo();
        kryo.register(Long.class);
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(SimpleObject.class);
        kryo.register(SimpleObject2.class);
        kryo.register(KryoMessage.class);


        final KryoMessage kryoMessage = new KryoMessage();

        SimpleObject2 simpleObject2 = new SimpleObject2();
        simpleObject2.setId("#id so2");
        simpleObject2.setValue(System.currentTimeMillis());


        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setId("@id so");
        simpleObject.setValue("" + System.currentTimeMillis());
        simpleObject.setSimpleObject2(simpleObject2);

        kryoMessage.setObject(simpleObject);
        kryoMessage.setStatus("Going from Client");



        this.kryoValueSerializer = new KryoValueSerializer(kryo);
        this.kryoValueDeserializer = new KryoValueDeserializer(kryo);


        ChannelPipeline pipeline = pipeline();

        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // Add the number codec first,
//        pipeline.addLast("decoder", new KryoDecoder(this.kryoValueDeserializer,who_is));
//        pipeline.addLast("encoder", new KryoEncoder(this.kryoValueSerializer,who_is));

        // and then business logic.
        pipeline.addLast("handler", new KryoClientHandler(kryoMessage, kryoValueSerializer, kryoValueDeserializer));

        return pipeline;
    }
}
