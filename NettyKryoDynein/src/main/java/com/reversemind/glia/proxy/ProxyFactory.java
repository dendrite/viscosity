package com.reversemind.glia.proxy;

import com.reversemind.glia.client.GliaClient;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * Date: 4/24/13
 * Time: 4:31 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ProxyFactory implements Serializable {

    private static final ProxyFactory proxyFactory = new ProxyFactory();

    private static GliaClient GliaClient;

    private ProxyFactory(){

    }

    public static ProxyFactory getInstance(GliaClient client){
        GliaClient = client;
        return proxyFactory;
    }

    public Object newProxyInstance(Class interfaceClass){
        ClassLoader classLoader = interfaceClass.getClassLoader();
        return Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, new ProxyHandler(GliaClient, interfaceClass));
    }
}
