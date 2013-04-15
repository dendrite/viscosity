package com.test.synthproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class CityHandlerRemote implements InvocationHandler {

    private KryoPayload kryoPayload;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Method:" + method.getName());

        for(Object obj: args){
            System.out.println("arg: " + obj.getClass().getCanonicalName() + " value:" + obj);
        }

        kryoPayload.setMethod(method);
        kryoPayload.setArgs(args);


        return "rrr";
    }
}
