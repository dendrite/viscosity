package com.reversemind.glia.client;

import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/30/13
 * Time: 2:12 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public interface IServerSelectorStrategy extends Serializable {
    public ServerMetadata selectServer(List<ServerMetadata> serverMetadataList);
}
