package com.reversemind.glia.proxy;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.client.IGliaClient;

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

    //TODO make it non-static
    private static IGliaClient gliaClient;

    private ProxyFactory(){
    }

    public static ProxyFactory getInstance(IGliaClient client){
        gliaClient = client;
        return proxyFactory;
    }

    public Object newProxyInstance(Class interfaceClass){

        ClassLoader classLoader = interfaceClass.getClassLoader();
        System.out.println(" ^^^^^^^^^^ GLIA PROXY FACTORY gliaClient:" + gliaClient);
        System.out.println(" ^^^^^^^^^^ GLIA PROXY FACTORY classLoader:" + classLoader);

        return Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, new ProxyHandler(gliaClient, interfaceClass));
    }
}
