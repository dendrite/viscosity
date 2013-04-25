package com.reversemind.glia;

import java.io.Serializable;

/**
 * Date: 4/24/13
 * Time: 3:04 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public enum GliaPayloadStatus {

    ERROR_UNKNOWN("ERROR_UNKNOWN", -1),                                     // Неизвестная ошибка
    ERROR_CLIENT_PAYLOAD("ERROR_CLIENT_PAYLOAD", -100),                      // Unknown client payload
    ERROR_PAYLOAD_UNKNOWN_METHOD("ERROR_PAYLOAD_UNKNOWN_METHOD", -111),                      // Unknown client payload
    ERROR_COULD_NOT_INIT_JNDI_CONTEXT("ERROR_COULD_NOT_INIT_JNDI_CONTEXT", -222),                      // Unknown client payload
    OK("OK",0);

    private String message;
    private int code;

    GliaPayloadStatus(String message, int code){
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static GliaPayloadStatus get(int code) {
        switch (code) {
            case 0:
                return OK;
            case -1:
                return ERROR_UNKNOWN;
            case -100:
                return ERROR_CLIENT_PAYLOAD;
            case -111:
                return ERROR_PAYLOAD_UNKNOWN_METHOD;
            case -222:
                return ERROR_COULD_NOT_INIT_JNDI_CONTEXT;
            default:
                return ERROR_UNKNOWN;
        }
    }

    public static int get(GliaPayloadStatus gliaPayloadStatus) {
        if (gliaPayloadStatus != null) {
            return gliaPayloadStatus.getCode();
        }
        return -1;
    }

    @Override
    public String toString(){
        return this.message;
    }
}
