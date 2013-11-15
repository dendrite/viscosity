package com.reversemind.glia.test.pojo.client;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.test.pojo.shared.ISimplePojo;
import com.reversemind.glia.test.pojo.shared.PAddressNode;
import com.reversemind.glia.test.pojo.shared.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Date: 4/24/13
 * Time: 5:06 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class RunClient {

    private final static Logger LOG = LoggerFactory.getLogger(RunClient.class);

    public static void main(String... args) throws Exception {
        LOG.info("Run Client");

        GliaClient client = new GliaClient(Settings.SERVER_HOST, Settings.SERVER_PORT);
        client.start();

        ISimplePojo simplePojoProxy = (ISimplePojo) ProxyFactory.getInstance().newProxyInstance(client, ISimplePojo.class);

        List<PAddressNode> list = simplePojoProxy.searchAddress("Moscow");

        if (list != null && list.size() > 0) {
            for (PAddressNode addressNode : list) {
                LOG.info("node:" + addressNode);
            }
        }
        client.shutdown();

        Thread.sleep(2000);

        LOG.info("Restart client");
        client.restart();

        Thread.sleep(100);

        list = simplePojoProxy.searchAddress("Moscow");

        if (list != null && list.size() > 0) {
            for (PAddressNode addressNode : list) {
                LOG.info("node:" + addressNode);
            }
        }

        client.shutdown();

    }

}
