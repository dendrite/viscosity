package com.reversemind.glia.server;

import java.io.Serializable;

/**
 * Basic GliaServer no any Zookeeper advertiser
 *
 * @author konilovsky
 * @since 1.4
 */
public class GliaServerSimple extends GliaServer implements IGliaServer, Serializable {

    /**
     * @param builder
     */
    public GliaServerSimple(GliaServerFactory.Builder builder) {
        super(builder);
    }
}
