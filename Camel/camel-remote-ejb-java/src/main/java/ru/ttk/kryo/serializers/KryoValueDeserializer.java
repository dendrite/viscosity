package ru.ttk.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        _kryoInput.setBuffer(ser);
        return _kryo.readClassAndObject(_kryoInput);
    }
}
