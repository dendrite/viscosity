package com.test.synthproxy.remote;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class CityHandlerServer implements InvocationHandler {

    Class interfaceClass;

    public CityHandlerServer(Class interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = method.invoke(this.interfaceClass, args);

        return result;
    }
}
