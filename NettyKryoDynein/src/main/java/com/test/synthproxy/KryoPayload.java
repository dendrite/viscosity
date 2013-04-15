package com.test.synthproxy;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 *
 */
public class KryoPayload implements Serializable {

    private Object result;
    private Method method;
    private Object[] args;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
