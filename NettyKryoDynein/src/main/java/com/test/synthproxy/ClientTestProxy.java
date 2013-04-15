package com.test.synthproxy;

import com.test.synthproxy.remote.CityHandler;
import com.test.synthproxy.shared.IPCity;
import com.test.synthproxy.local.PCity;
import com.test.synthproxy.local.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 *
 *
 */
public class ClientTestProxy {

    public static void main(String... args){
    ClassLoader classLoader = IPCity.class.getClassLoader();

        IPCity c = new PCity();

        IPCity proxy = (IPCity) Proxy.newProxyInstance(classLoader,
                new Class[]{IPCity.class},new CityHandler(c));

        System.out.println(proxy.createString("FFF"));

        IPCity proxyRemote = (IPCity) ProxyFactory.getInstance().newProxyInstance(IPCity.class);
        System.out.println("Remote server response:" + proxyRemote.createString("EEE"));

    }

}
