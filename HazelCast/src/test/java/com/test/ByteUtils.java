package com.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 *
 */
public class ByteUtils {


    public static byte[] convert(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] result = null;
        try {

            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(object);
            result = byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return result;
    }


}
