package com.reversemind.glia.test;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.shared.ISimplePojo;
import com.reversemind.glia.shared.PAddressNode;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/24/13
 * Time: 7:07 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class RunClientMulti implements Serializable {

    static GliaClient client;

    public static void main(String... args) throws Exception {
        System.out.println("Run client");

        int serverPort = 7000;
        String serverHost = "localhost";

        client = new GliaClient(serverHost,serverPort);
        client.run();

        for(int i=0; i<10;i++){
            new Thread(){
                long vl = System.currentTimeMillis();
                public void run() {
                    ISimplePojo simplePojoProxy = (ISimplePojo) ProxyFactory.getInstance(client).newProxyInstance(ISimplePojo.class);

                    List<PAddressNode> list = simplePojoProxy.searchAddress("Москва");

                    if (list != null && list.size() > 0) {
                        for (PAddressNode addressNode : list) {
                            System.out.println("" + this.getName() +"-node:" + addressNode);
                        }
                    }
                }
            }.run();
            Thread.sleep(10);
        }





    }

}
