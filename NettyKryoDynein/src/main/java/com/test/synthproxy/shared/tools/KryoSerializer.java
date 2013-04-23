package com.test.synthproxy.shared.tools;

import java.io.*;

/**
 *
 */
public class KryoSerializer {

    public byte[] serialize(KryoPayload kryoPayload) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(kryoPayload);
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public KryoPayload deserialize(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        KryoPayload kryoPayload = null;
        try {
            in = new ObjectInputStream(bis);
            kryoPayload = (KryoPayload)in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return kryoPayload;
    }

}
