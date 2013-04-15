package com.test.synthproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class CityHandler implements InvocationHandler {

    private IPCity ipCity;

    public CityHandler(IPCity ipCity){
        this.ipCity = ipCity;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object result = method.invoke(this.ipCity, args);
        System.out.println("after");
        return result;
    }
}
