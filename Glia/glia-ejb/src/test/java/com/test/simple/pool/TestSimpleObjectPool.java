package com.test.simple.pool;

import com.reversemind.glia.client.GliaClientServerDiscovery;
import com.reversemind.glia.client.IGliaClient;
import com.test.pool.ClientFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.junit.Test;

import java.util.NoSuchElementException;

/**
 *
 */
public class TestSimpleObjectPool {

    @Test
    public void testSimplePool() throws Exception {

        SimpleFactory simpleFactory = new SimpleFactory();
        GenericObjectPool<String> pool = new GenericObjectPool<String>(simpleFactory, 10, GenericObjectPool.WHEN_EXHAUSTED_GROW, 2 * 1000);

        String[] strs = new String[10];
        for(int i=0; i<10; i++){
            strs[i] = pool.borrowObject();
        }

        for(String string: strs){
            System.out.println( "|" + string);
        }

        Thread.sleep(5 * 1000);

        String extra = null;
        try{
            extra = pool.borrowObject();
        }catch(NoSuchElementException nex){
            nex.printStackTrace();
            System.out.println("!!!");
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("!!!!");
        }


        System.out.println("Get extra from pool:" + extra);
    }

}