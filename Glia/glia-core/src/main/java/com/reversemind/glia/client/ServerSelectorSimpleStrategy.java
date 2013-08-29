package com.reversemind.glia.client;

import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Date: 4/30/13
 * Time: 2:11 PM
 *
 * @author
 * @since 1.0
 */
public class ServerSelectorSimpleStrategy implements IServerSelectorStrategy, Serializable {

    public ServerMetadata selectServer(List<ServerMetadata> serverMetadataList) {
        if (serverMetadataList != null && serverMetadataList.size() > 0) {

            if(serverMetadataList.size() == 1){
                return serverMetadataList.get(0);
            }

            Collections.sort(serverMetadataList, new Comparator<ServerMetadata>() {
                @Override
                public int compare(ServerMetadata o1, ServerMetadata o2) {
                    return (int)(o2.getMetrics().getStartDate().getTime() - o1.getMetrics().getStartDate().getTime());
                }
            });

            return serverMetadataList.get(0);
        }
        return null;
    }
}
