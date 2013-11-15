package com.reversemind.glia;

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

    private Throwable throwable;

    private GliaPayloadStatus status;

    public GliaPayload() {
        this.resultResponse = null;
        this.methodName = null;
        this.arguments = null;
        this.interfaceClass = null;
        this.clientTimestamp = System.currentTimeMillis();
        this.serverTimestamp = System.currentTimeMillis();
        this.status = GliaPayloadStatus.ERROR_UNKNOWN;
        this.throwable = null;
    }

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

    public GliaPayloadStatus getStatus() {
        return status;
    }

    public void setStatus(GliaPayloadStatus status) {
        this.status = status;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
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
                ", status=" + status +
                ", throwable=" + throwable +
                '}';
    }
}
