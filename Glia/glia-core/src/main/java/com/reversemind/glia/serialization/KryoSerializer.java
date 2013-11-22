package com.reversemind.glia.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

/**
 * Copyright (c) 2013 Eugene Kalinin
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class KryoSerializer {
    Kryo _kryo;
    Output _kryoOut;

    public KryoSerializer(Kryo kryo) {
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
