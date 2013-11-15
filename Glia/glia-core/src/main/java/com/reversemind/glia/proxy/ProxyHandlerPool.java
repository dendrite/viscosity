package com.reversemind.glia.proxy;

import com.reversemind.glia.client.ClientPool;
import com.reversemind.glia.client.ClientPoolFactory;
import com.reversemind.glia.client.IGliaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class ProxyHandlerPool extends AbstractProxyHandler implements InvocationHandler {

    private final static Logger LOG = LoggerFactory.getLogger(ProxyHandlerPool.class);

    private ClientPool clientPool;
    private Class interfaceClass;
    private IGliaClient gliaClient = null;

    public ProxyHandlerPool(ClientPool clientPool, Class interfaceClass) {
        this.clientPool = clientPool;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public IGliaClient getGliaClient() throws Exception {
        if (this.gliaClient == null) {
            try {
                synchronized (this.clientPool) {
                    this.gliaClient = this.clientPool.borrowObject();
                }
            } catch (Exception ex) {
                LOG.error("Could not get a gliaClient from Pool #1 - try again", ex);

                try {
                    Thread.sleep(300);
                    synchronized (this.clientPool) {
                        this.gliaClient = this.clientPool.borrowObject();
                    }
                } catch (Exception ex2) {
                    LOG.error("Could not get a gliaClient from Pool #2 - Try to reload pool", ex2);

                    try {
                        ClientPoolFactory clientPoolFactory = this.clientPool.getClientPoolFactory();
                        this.clientPool.clear();
                        this.clientPool.close();
                        this.clientPool = null;
                        this.clientPool = new ClientPool(clientPoolFactory);

                        synchronized (this.clientPool) {
                            this.gliaClient = this.clientPool.borrowObject();
                        }
                    } catch (Exception ex3) {
                        LOG.error("Could not get a glia client after reloaded pool #3", ex3);
                    }

                }

            }
        }
        LOG.warn("Pool METRICS:" + this.clientPool.printPoolMetrics());
        return this.gliaClient;
    }

    @Override
    public Class getInterfaceClass() {
        return this.interfaceClass;
    }

    @Override
    public void returnClient() throws Exception {
        if (this.gliaClient != null) {
            synchronized (this.gliaClient) {
                try {
                    this.clientPool.returnObject(this.gliaClient);
                } catch (Exception ex) {
                    LOG.error("EXCEPTION COULD NOT RETURN gliaClient into Pool", ex);
                }
                this.gliaClient = null;
            }
        }
    }

    @Override
    public void returnClient(IGliaClient gliaClient) throws Exception {
        if (gliaClient != null) {
            synchronized (gliaClient) {
                try {
                    this.clientPool.returnObject(gliaClient);
                } catch (Exception ex) {
                    LOG.error("EXCEPTION COULD NOT RETURN gliaClient into Pool #2", ex);
                }
                gliaClient = null;
            }
        }
    }

}
