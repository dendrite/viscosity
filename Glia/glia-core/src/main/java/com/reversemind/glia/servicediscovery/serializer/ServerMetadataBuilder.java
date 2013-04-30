package com.reversemind.glia.servicediscovery.serializer;

import com.reversemind.glia.server.GliaServer;

import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 10:38 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ServerMetadataBuilder implements Serializable {

    public ServerMetadata build(GliaServer gliaServer){
        if(gliaServer == null){
            return null;
        }
        return new ServerMetadata(
                gliaServer.getName(),
                gliaServer.getInstanceName(),
                "localhost",
                gliaServer.getPort()
        );
    }

}
