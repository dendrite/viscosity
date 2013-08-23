package com.reversemind.glia;

import java.io.Serializable;

/**
 * Date: 4/26/13
 * Time: 2:54 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaPayloadBuilder implements Serializable {

    /**
     *
     * @param gliaPayloadStatus
     * @return
     */
    public static GliaPayload buildErrorPayload(GliaPayloadStatus gliaPayloadStatus) {
        GliaPayload gliaPayload = new GliaPayload();
        gliaPayload.setServerTimestamp(System.currentTimeMillis());
        gliaPayload.setStatus(gliaPayloadStatus);
        return gliaPayload;
    }

    public static GliaPayload buildErrorPayload(GliaPayloadStatus gliaPayloadStatus, Throwable throwable) {
        GliaPayload gliaPayload = new GliaPayload();
        gliaPayload.setServerTimestamp(System.currentTimeMillis());
        gliaPayload.setStatus(gliaPayloadStatus);
        gliaPayload.setThrowable(throwable);
        return gliaPayload;
    }

}
