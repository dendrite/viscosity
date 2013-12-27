package ru.ttk.hypergate.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import java.io.IOException;

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
public class KryoDeserializer {
    Kryo _kryo;
    Input _kryoInput;

    public KryoDeserializer(Kryo kryo) {
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
