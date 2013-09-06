package com.reversemind.glia.proxy;

import com.reversemind.glia.client.ClientPool;
import com.reversemind.glia.client.ClientPoolFactory;
import com.reversemind.glia.client.IGliaClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class ProxyHandlerPool extends AbstractProxyHandler implements InvocationHandler {

    private ClientPool clientPool;
    private Class interfaceClass;
    private IGliaClient gliaClient = null;

    public ProxyHandlerPool(ClientPool clientPool, Class interfaceClass) {
        this.clientPool = clientPool;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public IGliaClient getGliaClient() throws Exception{
        if(this.gliaClient == null){
//            synchronized (this.gliaClient){
            try{
                this.gliaClient = this.clientPool.borrowObject();
            }catch (Exception ex){
                ex.printStackTrace();
                System.out.println("Try to reload pool");
                ClientPoolFactory clientPoolFactory = this.clientPool.getClientPoolFactory();
                this.clientPool.clear();
                this.clientPool.close();
                this.clientPool = null;
                this.clientPool = new ClientPool(clientPoolFactory);

                this.gliaClient = this.clientPool.borrowObject();
            }


//            }
        }
        System.out.println("PPOL METRICS:" + this.clientPool.printPoolMetrics());
        return this.gliaClient;
    }

    @Override
    public Class getInterfaceClass() {
        return this.interfaceClass;
    }

    @Override
    public void returnClient() throws Exception {
        if(this.gliaClient != null){
            synchronized (this.gliaClient){
                this.clientPool.returnObject(this.gliaClient);
//                this.gliaClient = null;
            }
        }
    }

}
