package ru.ttk.netty.echo.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import java.io.IOException;

/**
 *
 */
public class KryoValueDeserializer {
    Kryo _kryo;
    Input _kryoInput;

    public KryoValueDeserializer(Kryo kryo) {
        _kryo = kryo;
        _kryoInput = new Input(1);
    }

    public Object deserialize(byte[] ser) throws IOException {
        // https://groups.google.com/forum/?fromgroups=#!topic/kryo-users/jU0XSDkrKkY
        /*
            protected <T> T deserialize( final byte[] in, final Class<T> clazz ) {
               final Input input = new Input(in);
               return _kryo.readObject(input, clazz);
            }
         */

        _kryoInput = new Input(1);
        _kryoInput.setBuffer(ser);
        //_kryoInput =  new Input(ser);
        return _kryo.readClassAndObject(_kryoInput);
    }
}
