package com.reversemind.glia.other.remote;

import cluster.AddressSearchResult;
import cluster.IAddressSearch;
import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/25/13
 * Time: 2:49 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class CallRemoteServer implements Serializable {

    public static void main(String... args) throws Exception {

        System.out.println("Run client");

        int serverPort = 7000;
        String serverHost = "localhost";

        GliaClient client = new GliaClient(serverHost,serverPort);
        client.start();

        IAddressSearch addressSearch = (IAddressSearch) ProxyFactory.getInstance().newProxyInstance(client, IAddressSearch.class);

        List<AddressSearchResult> list = addressSearch.doSearch("Чонгарский");
        if(list != null && list.size() > 0){
            for(AddressSearchResult result: list){
                System.out.println("--" + result);
            }
        }

    }

}
