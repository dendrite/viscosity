package com.test.synthproxy.local.hide;

import com.test.synthproxy.remote.RemoteServer;
import com.test.synthproxy.shared.tools.KryoPayload;
import com.test.synthproxy.shared.tools.KryoSerializer;

/**
 *
 */
public class Client {

    public KryoPayload send(KryoPayload kryoPayload){

        KryoSerializer kryoSerializer = new KryoSerializer();

        RemoteServer remoteServer = new RemoteServer();
        byte[] bytes = remoteServer.conversation(kryoSerializer.serialize(kryoPayload));

        if(bytes != null){
            return kryoSerializer.deserialize(bytes);
        }

        return null;
    }

}
