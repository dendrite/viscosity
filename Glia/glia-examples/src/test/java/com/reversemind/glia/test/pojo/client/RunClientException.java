package com.reversemind.glia.test.pojo.client;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.test.pojo.shared.ISimplePojo;
import com.reversemind.glia.test.pojo.shared.SimpleException;

/**
 *
 */
public class RunClientException {

    public static void main(String... args) throws Exception {
        System.out.println("Run EXCEPTION client");

        int serverPort = 7012;
        String serverHost = "localhost";

        GliaClient client = new GliaClient(serverHost, serverPort);
        client.start();

        ISimplePojo simplePojoProxy = (ISimplePojo) ProxyFactory.getInstance().newProxyInstance(client, ISimplePojo.class);

        System.out.println("\n\n=======================");

        try{
            String simple = simplePojoProxy.createException("Simple");
        }catch (SimpleException ex){
            System.out.println("I've got an SimpleException:" + ex);
        }
    }
}
