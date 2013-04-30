package com.reversemind.glia.client;

import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 2:01 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaClientSelfDiscovery extends GliaClient implements Serializable {

    public GliaClientSelfDiscovery(String host, int port) {
        super(host, port);
    }

}
