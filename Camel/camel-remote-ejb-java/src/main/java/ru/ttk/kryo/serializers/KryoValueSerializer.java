package ru.ttk.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class KryoValueSerializer {
    Kryo _kryo;
    Output _kryoOut;

    public KryoValueSerializer(Kryo kryo) {
        this._kryo = kryo;
        _kryoOut = new Output(2000, 2000000000);
    }

    public byte[] serialize(Object obj) {
        _kryoOut.clear();
        _kryo.writeClassAndObject(_kryoOut, obj);
        return _kryoOut.toBytes();
    }
}