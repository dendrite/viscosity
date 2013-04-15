package com.test.echo.client;

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
public class KryoClientHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = Logger.getLogger(KryoClientHandler.class.getName());

    private KryoValueSerializer kryoValueSerializer;
    private KryoValueDeserializer kryoValueDeserializer;

    private final ChannelBuffer firstMessage;



    public KryoClientHandler(KryoMessage kryoMessage, KryoValueSerializer kryoValueSerializer, KryoValueDeserializer kryoValueDeserializer){
        this.kryoValueSerializer = kryoValueSerializer;
        this.kryoValueDeserializer = kryoValueDeserializer;

        byte[] bytes = kryoValueSerializer.serialize(kryoMessage);

        firstMessage = ChannelBuffers.buffer(bytes.length);
        firstMessage.writeBytes(bytes);


        System.out.println("======= ДЛИНА:" + bytes.length);

    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        // Send the first message.  Server will not send anything here
        // because the firstMessage's capacity is 0.
        e.getChannel().write(firstMessage);
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            logger.info("Client state:" + e.toString());
        }

        // Let SimpleChannelHandler call actual event handler methods below.
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        try {

            KryoMessage kryoMessageHere = (KryoMessage)kryoValueDeserializer.deserialize(((ChannelBuffer) e.getMessage()).array());

            if(kryoMessageHere != null){
                System.out.println("i've got kryoMessageHere" + kryoMessageHere);
                System.out.println("Status length:" + kryoMessageHere.getStatus().length() + "\n\n");

                kryoMessageHere.setStatus( kryoMessageHere.getStatus() + " client:" + System.currentTimeMillis() );

//            ChannelBuffer cb;
//            cb = ((ChannelBuffer) e.getMessage());
//              cb.writeBytes(kryoValueSerializer.serialize(kryoMessageHere));

                byte[] bytes = kryoValueSerializer.serialize(kryoMessageHere);
                //ChannelBuffer cb = ChannelBuffers.buffer(bytes.length);

                ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
                buf.writeBytes(bytes);      // data
                e.getChannel().write(buf);
            }

           // new Exception("Client get message from server");
//            // Send back the received message to the remote peer.
//            transferredBytes.addAndGet(((ChannelBuffer) e.getMessage()).readableBytes());
//            e.getChannel().write(e.getMessage());

        } catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

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
