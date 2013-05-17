package com.reversemind.glia.client;

import java.io.Serializable;

/**
 * Date: 5/17/13
 * Time: 8:40 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public interface IGliaClient extends Serializable {
    public void start() throws Exception;
    public void shutdown();
}
