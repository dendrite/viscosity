package com.test.echo.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import com.test.echo.kryo.KryoMessage;
import com.test.echo.kryo.KryoValueDeserializer;
import com.test.echo.kryo.KryoValueSerializer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class KryoServerHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = Logger.getLogger(KryoServerHandler.class.getName());

    private KryoValueSerializer kryoValueSerializer;
    private KryoValueDeserializer kryoValueDeserializer;




    public KryoServerHandler(KryoValueSerializer kryoValueSerializer, KryoValueDeserializer kryoValueDeserializer){
        this.kryoValueDeserializer = kryoValueDeserializer;
        this.kryoValueSerializer = kryoValueSerializer;
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            logger.info("Channel state:" + e.toString());
        }

        // Let SimpleChannelHandler call actual event handler methods below.
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

        byte[] bytes = ((ChannelBuffer) e.getMessage()).array();

        KryoMessage kryoMessage = null;
        try {
            kryoMessage = (KryoMessage)kryoValueDeserializer.deserialize(bytes);
            System.out.println("Server get message:" + kryoMessage);

            if(kryoMessage != null){
                kryoMessage.setStatus( kryoMessage.getStatus() + " server:" + System.currentTimeMillis());

                byte[] bytes2 = kryoValueSerializer.serialize(kryoMessage);


                ChannelBuffer cb = ChannelBuffers.buffer(bytes2.length);
                cb.writeBytes(bytes2);
                e.getChannel().write(cb);
            } else{

                e.getChannel().write(e.getMessage());
            }


        } catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        // Discard received data silently by doing nothing.
        // transferredBytes += ((ChannelBuffer) e.getStatus()).readableBytes();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        // Close the connection when an exception is raised.
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.",
                e.getCause());
        e.getChannel().close();
    }

}
