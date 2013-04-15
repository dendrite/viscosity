package com.test.synthproxy;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ProxyFactory {

    private static final ProxyFactory proxyFactory = new ProxyFactory();

    private Map<Class,Class> map = new HashMap<Class, Class>();

    private ProxyFactory(){
        System.out.println("go");
        map.put(IPCity.class,CityHandler.class);
    }

    public static ProxyFactory getInstance(){
        return proxyFactory;
    }

    public Object newProxyInstance(Class clazz){

        if(map.get(clazz) != null){
            ClassLoader classLoader = clazz.getClassLoader();
            IPCity proxy = (IPCity) Proxy.newProxyInstance(classLoader,
                    new Class[]{IPCity.class}, new CityHandlerRemote());
            return proxy;
        }

        return null;

    }


}
