package com.test.echo.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import com.test.echo.kryo.KryoMessage;
import com.test.echo.kryo.KryoValueSerializer;

/**
 *
 */
public class KryoEncoder extends OneToOneEncoder {

    private KryoValueSerializer kryoValueSerializer;
    private String who_is = "someone";

    public KryoEncoder(KryoValueSerializer kryoValueSerializer, String who_is){
        super();
        this.kryoValueSerializer = kryoValueSerializer;
        this.who_is = who_is;
    }

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {

        if(!(message instanceof KryoMessage))
            return message; // Если это не пакет, то просто пропускаем его дальше
        KryoMessage kryoMessage = (KryoMessage) message;

        kryoMessage.setStatus( kryoMessage.getStatus() + " " + this.who_is + ":" + System.currentTimeMillis());

        byte[] bytes = kryoValueSerializer.serialize(kryoMessage);

        // Создаём динамический буфер для записи в него данных из пакета.
        // Если Вы точно знаете длину пакета, Вам не обязательно использовать динамический буфер —
        // ChannelBuffers предоставляет и буферы фиксированной длинны, они могут быть эффективнее.
        ChannelBuffer buffer = ChannelBuffers.buffer(bytes.length);
        buffer.writeBytes(bytes);

        return buffer; // Возвращаем буфер, который и будет записан в канал
    }
}
