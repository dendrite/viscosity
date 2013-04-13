package ru.ttk.netty.echo.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.jboss.netty.handler.codec.replay.VoidEnum;
import ru.ttk.netty.echo.kryo.KryoMessage;
import ru.ttk.netty.echo.kryo.KryoValueDeserializer;

/**
 * Decodes a received {@link ChannelBuffer} into a {@link String}.  Please
 * note that this decoder must be used with a proper {@link org.jboss.netty.handler.codec.frame.FrameDecoder}
 * such as {@link org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder} if you are using a stream-based
 * transport such as TCP/IP.  A typical setup for a text-based line protocol
 * in a TCP/IP socket would be:
 * <pre>
 * {@link org.jboss.netty.channel.ChannelPipeline} pipeline = ...;
 *
 * // Decoders
 * pipeline.addLast("frameDecoder", new {@link org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder}(80, {@link org.jboss.netty.handler.codec.frame.Delimiters#lineDelimiter()}));
 * pipeline.addLast("stringDecoder", new {@link StringDecoder}(CharsetUtil.UTF_8));
 *
 * // Encoder
 * pipeline.addLast("stringEncoder", new {@link org.jboss.netty.handler.codec.string.StringEncoder}(CharsetUtil.UTF_8));
 * </pre>
 * and then you can use a {@link String} instead of a {@link ChannelBuffer}
 * as a message:
 * <pre>
 * void messageReceived({@link ChannelHandlerContext} ctx, {@link org.jboss.netty.channel.MessageEvent} e) {
 *     String msg = (String) e.getMessage();
 *     ch.write("Did you say '" + msg + "'?\n");
 * }
 * </pre>
 *
 * @apiviz.landmark
 */
public class KryoDecoder extends OneToOneDecoder {

    private KryoValueDeserializer kryoValueDeserializer;
    private String who_is = "someone";

    public KryoDecoder(KryoValueDeserializer kryoValueDeserializer,  String who_is){
        super();
        this.kryoValueDeserializer = kryoValueDeserializer;
        this.who_is = who_is;
    }
//
//    @Override
//    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//        ctx.sendUpstream(e);
//    }
//
//    @Override
//    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//        ctx.sendUpstream(e);
//    }
//
//    @Override
//    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, VoidEnum state) throws Exception {
//        //return Packet.read(buffer);
//        System.out.println("WHO_IS:" + who_is);
//        return kryoValueDeserializer.deserialize(buffer.array());
//    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {

        if (!(msg instanceof ChannelBuffer)) {
            return msg;
        }

        ChannelBuffer buffer = (ChannelBuffer) msg;
        if(buffer.hasArray()){
            KryoMessage kryoMessage = (KryoMessage) kryoValueDeserializer.deserialize(((ChannelBuffer) msg).array());


            System.out.println("-- DECODED: message:" + kryoMessage);
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}



// FROM HABRAHABR
//
//
//public class KryoDecoder extends ReplayingDecoder<VoidEnum> {
//
//    private KryoValueDeserializer kryoValueDeserializer;
//    private String who_is = "someone";
//
//    public KryoDecoder(KryoValueDeserializer kryoValueDeserializer,  String who_is){
//        super();
//        this.kryoValueDeserializer = kryoValueDeserializer;
//        this.who_is = who_is;
//    }
//
//    @Override
//    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//        ctx.sendUpstream(e);
//    }
//
//    @Override
//    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//        ctx.sendUpstream(e);
//    }
//
//    @Override
//    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, VoidEnum state) throws Exception {
//        //return Packet.read(buffer);
//        System.out.println("WHO_IS:" + who_is);
//        return kryoValueDeserializer.deserialize(buffer.array());
//    }
//}