package com.reversemind.glia.client;

import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/30/13
 * Time: 2:11 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ServerSelectorSimpleStrategy implements IServerSelectorStrategy, Serializable {

    public ServerMetadata selectServer(List<ServerMetadata> serverMetadataList) {
        if (serverMetadataList != null && serverMetadataList.size() > 0) {
            return serverMetadataList.get(0);
        }
        return null;
    }
}
