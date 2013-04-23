package com.test.synthproxy.local;

import com.test.synthproxy.shared.IPCity;
import com.test.synthproxy.local.ProxyFactory;
import com.test.synthproxy.shared.PClient;

/**
 *
 *
 */
public class ClientTestProxy {

    public static void main(String... args){
        ClassLoader classLoader = IPCity.class.getClassLoader();

        IPCity proxy = (IPCity) ProxyFactory.getInstance().newProxyInstance(IPCity.class);
        System.out.println("Remote server response:" + proxy.createString("EEE"));


        PClient client = proxy.getClientForAddress("SIMPLE ADDRESS");
        System.out.println("Client:" + client);

    }

}
