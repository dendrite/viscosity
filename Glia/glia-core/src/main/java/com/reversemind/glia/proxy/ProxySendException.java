package com.reversemind.glia.proxy;

/**
 *
 */
public class ProxySendException extends Exception{

    public ProxySendException(String message){
        super(message);
    }

    public ProxySendException(String msg, Throwable cause) {
        super(msg, cause);
    }

}