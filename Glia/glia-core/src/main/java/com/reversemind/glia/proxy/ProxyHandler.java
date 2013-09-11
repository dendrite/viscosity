package com.reversemind.glia.proxy;

import com.reversemind.glia.GliaPayload;
import com.reversemind.glia.GliaPayloadStatus;
import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.client.IGliaClient;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Date: 4/24/13
 * Time: 4:33 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ProxyHandler extends AbstractProxyHandler implements InvocationHandler {

    private IGliaClient gliaClient;
    private Class interfaceClass;

    public ProxyHandler(IGliaClient gliaClient, Class interfaceClass){
        this.gliaClient = gliaClient;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public IGliaClient getGliaClient() throws Exception {
        return this.gliaClient;
    }

    @Override
    public Class getInterfaceClass() {
        return this.interfaceClass;
    }

    @Override
    public void returnClient() {
    }

    @Override
    public void returnClient(IGliaClient gliaClient) throws Exception {
    }
}
