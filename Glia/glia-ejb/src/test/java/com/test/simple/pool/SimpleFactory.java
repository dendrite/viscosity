package com.test.simple.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 *
 */
public class SimpleFactory extends BasePoolableObjectFactory<String> {

    public static int count = 0;

    @Override
    public String makeObject() throws Exception {
//        Thread.sleep(3*1000);
        return "RES:" + count++;
    }
}
