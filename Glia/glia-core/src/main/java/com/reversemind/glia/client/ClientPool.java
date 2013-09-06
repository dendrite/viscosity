package com.reversemind.glia.client;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 *
 */
public class ClientPool extends GenericObjectPool<IGliaClient> {

    /**
     * Right now only max 5 clients
     *
     *
     * @param clientPoolFactory
     */
    public ClientPool(ClientPoolFactory clientPoolFactory){
        // int maxActive, byte whenExhaustedAction, long maxWait
        super(clientPoolFactory, 5, (byte)1, 30 * 1000);
    }

    public String printPoolMetrics(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" getMaxActive:").append(this.getMaxActive()).append("\n");
        stringBuffer.append(" getMaxIdle:").append(this.getMaxIdle()).append("\n");
        stringBuffer.append(" getMaxWait:").append(this.getMaxWait()).append("\n");

        stringBuffer.append(" getNumActive:").append(this.getNumActive()).append("\n");
        stringBuffer.append(" getNumIdle:").append(this.getNumIdle()).append("\n");
        stringBuffer.append(" getNumTestsPerEvictionRun:").append(this.getNumTestsPerEvictionRun()).append("\n");
        return stringBuffer.toString();
    }
}
