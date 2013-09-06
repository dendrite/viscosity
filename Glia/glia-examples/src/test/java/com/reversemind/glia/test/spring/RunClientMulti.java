package com.reversemind.glia.test.spring;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.test.pojo.shared.ISimplePojo;
import com.reversemind.glia.test.pojo.shared.PAddressNode;

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
        client.start();

        Thread[] threadArray = new Thread[10];

        for(int i=0; i<10;i++){
            threadArray[i] = new Thread(){
                long vl = System.currentTimeMillis();
                public void run() {

                    for(int i=0;i<1;i++){
                        ISimplePojo simplePojoProxy = (ISimplePojo) ProxyFactory.getInstance().newProxyInstance(client, ISimplePojo.class);

                        List<PAddressNode> list = simplePojoProxy.searchAddress(this.getName());

                        if (list != null && list.size() > 0) {
                            for (PAddressNode addressNode : list) {
                                System.out.println("" + this.getName() +"-node:" + addressNode);
                            }
                        }
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }

                }
            };
            //Thread.sleep(1);
        }

        for(int i=0; i<10;i++){
            threadArray[i].start();
        }




    }

}
