package com.reversemind.glia.proxy;

import com.reversemind.glia.GliaPayload;
import com.reversemind.glia.GliaPayloadStatus;
import com.reversemind.glia.client.GliaClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Date: 4/24/13
 * Time: 4:33 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ProxyHandler implements InvocationHandler {

    private GliaClient gliaClient;
    private Class interfaceClass;

    public ProxyHandler(GliaClient gliaClient, Class interfaceClass){
        this.gliaClient = gliaClient;
        this.interfaceClass = interfaceClass;
    }

    private GliaPayload makePayload(){
        return new GliaPayload();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("\n\n\n" +
                "!!!!!!!!!!!!!!!!\n" +
                "Invoke REMOTE METHOD\n\n\n");

        System.out.println("Method:" + method.getName());
        for(Object obj: args){
            System.out.println("arguments: " + obj.getClass().getCanonicalName() + " value:" + obj);
        }

        GliaPayload gliaPayload = this.makePayload();

        gliaPayload.setClientTimestamp(System.currentTimeMillis());
        gliaPayload.setMethodName(method.getName());
        gliaPayload.setArguments(args);
        gliaPayload.setInterfaceClass(this.interfaceClass);

        gliaClient.send(gliaPayload);

        Thread.sleep(100);
        GliaPayload fromServer = gliaClient.getGliaPayload();

        if(fromServer!=null && fromServer.getStatus() == GliaPayloadStatus.OK){
            return fromServer.getResultResponse();
        }

        throw new Exception("Something wrong with server");
    }
}
