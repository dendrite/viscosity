package ru.ttk.glia;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Date: 4/24/13
 * Time: 10:09 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaPayload implements Serializable {

    private Object resultResponse;
    private String methodName;
    private Object[] arguments;
    private Class interfaceClass;

    private long clientTimestamp;
    private long serverTimestamp;

    public Object getResultResponse() {
        return resultResponse;
    }

    public void setResultResponse(Object resultResponse) {
        this.resultResponse = resultResponse;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public long getClientTimestamp() {
        return clientTimestamp;
    }

    public void setClientTimestamp(long clientTimestamp) {
        this.clientTimestamp = clientTimestamp;
    }

    public long getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(long serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    @Override
    public String toString() {
        return "GliaPayload{" +
                "resultResponse=" + resultResponse +
                ", methodName='" + methodName + '\'' +
                ", arguments=" + (arguments == null ? null : Arrays.asList(arguments)) +
                ", interfaceClass=" + interfaceClass +
                ", clientTimestamp=" + clientTimestamp +
                ", serverTimestamp=" + serverTimestamp +
                '}';
    }
}
