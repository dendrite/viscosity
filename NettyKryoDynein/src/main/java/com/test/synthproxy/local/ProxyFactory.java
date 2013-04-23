package com.test.synthproxy.local;

import com.test.synthproxy.local.hide.CityHandler;
import com.test.synthproxy.shared.IPCity;

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

    public Object newProxyInstance(Class interfaceClass){

        if(map.get(interfaceClass) != null){
            ClassLoader classLoader = interfaceClass.getClassLoader();
            return Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, new CityHandlerRemote(interfaceClass));
        }

        return null;

    }


}
