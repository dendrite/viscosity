package com.test.echo.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

/**
 *
 */
public class KryoValueSerializer {
    Kryo _kryo;
    Output _kryoOut;

    public KryoValueSerializer(Kryo kryo) {
        this._kryo = kryo;
        // saw something in storm project
        _kryoOut = new Output(2000, 2000000000);
    }

    public byte[] serialize(Object obj) {
        _kryoOut.clear();
        _kryo.writeClassAndObject(_kryoOut, obj);
        return _kryoOut.toBytes();
    }
}
