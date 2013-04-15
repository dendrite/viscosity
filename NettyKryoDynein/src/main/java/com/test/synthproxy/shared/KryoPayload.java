package com.test.synthproxy.shared;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 *
 */
public class KryoPayload implements Serializable {

    private Object result;
    private String methodName;
    private Object[] args;
    private Class interfaceClass;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
