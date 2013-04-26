package com.reversemind.glia.both.simple;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.shared.ISimplePojo;
import com.reversemind.glia.shared.PAddressNode;
import sun.security.provider.SystemSigner;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/24/13
 * Time: 5:06 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class RunClient {

    public static void main(String... args) throws Exception {
        System.out.println("Run client");

        int serverPort = 7000;
        String serverHost = "localhost";

        GliaClient client = new GliaClient(serverHost,serverPort);
        client.run();


        ISimplePojo simplePojoProxy = (ISimplePojo) ProxyFactory.getInstance(client).newProxyInstance(ISimplePojo.class);


        List<PAddressNode> list = null;
        long bT = System.currentTimeMillis();
        for(int i=0; i<100; i++){
            list = simplePojoProxy.searchAddress("Москва+" + i);
        }
        System.out.println("\n\n\n\n ALL 100 for time:" + (System.currentTimeMillis() - bT));


        if(list != null && list.size() > 0){
            for(PAddressNode addressNode: list){
                System.out.println("node:" + addressNode);
            }
        }


    }

}
