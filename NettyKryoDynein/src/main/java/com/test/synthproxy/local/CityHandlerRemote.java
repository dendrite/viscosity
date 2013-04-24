package com.test.synthproxy.local;

import com.test.synthproxy.local.hide.Client;
import com.test.synthproxy.shared.tools.KryoPayload;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class CityHandlerRemote implements InvocationHandler {

    private KryoPayload kryoPayload;
    private Class interfaceClass;

    public CityHandlerRemote(Class interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Method:" + method.getName());

        for(Object obj: args){
            System.out.println("arg: " + obj.getClass().getCanonicalName() + " value:" + obj);
        }

        kryoPayload = new KryoPayload();
        kryoPayload.setMethodName(method.getName());
        kryoPayload.setArgs(args);
        kryoPayload.setInterfaceClass(this.interfaceClass);

        Client client = new Client();
        kryoPayload = client.send(kryoPayload);

        return kryoPayload.getResult();

    }
}
