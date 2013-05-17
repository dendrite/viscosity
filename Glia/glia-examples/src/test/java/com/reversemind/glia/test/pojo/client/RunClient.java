package com.reversemind.glia.test.pojo.client;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.test.pojo.shared.ISimplePojo;
import com.reversemind.glia.test.pojo.shared.PAddressNode;

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

        GliaClient client = new GliaClient(serverHost, serverPort);
        client.start();


        ISimplePojo simplePojoProxy = (ISimplePojo) ProxyFactory.getInstance(client).newProxyInstance(ISimplePojo.class);

        List<PAddressNode> list = simplePojoProxy.searchAddress("Москва");

        if (list != null && list.size() > 0) {
            for (PAddressNode addressNode : list) {
                System.out.println("node:" + addressNode);
            }
        }


        client.shutdown();
    }

}
