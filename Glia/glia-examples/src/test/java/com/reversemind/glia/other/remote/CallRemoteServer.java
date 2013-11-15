package com.reversemind.glia.other.remote;

import cluster.AddressSearchResult;
import cluster.IAddressSearch;
import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(CallRemoteServer.class);

    public static void main(String... args) throws Exception {

        LOG.debug("Run client");

        int serverPort = 7000;
        String serverHost = "localhost";

        GliaClient client = new GliaClient(serverHost, serverPort);
        client.start();

        IAddressSearch addressSearch = (IAddressSearch) ProxyFactory.getInstance().newProxyInstance(client, IAddressSearch.class);

        List<AddressSearchResult> list = addressSearch.doSearch("Чонгарский");
        if (list != null && list.size() > 0) {
            for (AddressSearchResult result : list) {
                LOG.debug("--" + result);
            }
        }

    }

}
