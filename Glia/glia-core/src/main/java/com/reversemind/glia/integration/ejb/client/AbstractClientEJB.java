package com.reversemind.glia.integration.ejb.client;

import com.reversemind.glia.client.ClientPool;
import com.reversemind.glia.client.ClientPoolFactory;
import com.reversemind.glia.proxy.ProxyFactoryPool;
import com.reversemind.glia.proxy.ProxySendException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;

/**
 *
 */
public abstract class AbstractClientEJB implements IClientEJB, Serializable {

    //protected IGliaClient client;
    protected ProxyFactoryPool proxyFactoryPool = null;
    private static ClientPool clientPool;

    @PostConstruct
    public void init(){
        // Wait a little -
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // LOG.error()
            e.printStackTrace();
        }
        this.localInit();
    }

    @PreDestroy
    public void destroy(){
        // if need to destroy pool
    }

    public abstract String getGliaClientBeanName();
    public abstract Class getGliaClientBeanClass();

    /**
     * Default value is "META-INF/glia-client-context.xml"
     *
     * @return
     */
    @Override
    public String getContextXML() {
        return "META-INF/glia-client-context.xml";
    }

    private void initPool(){
        if(clientPool == null){
//            synchronized (clientPool){
                ClientPoolFactory clientPoolFactory = new ClientPoolFactory(this.getContextXML(), this.getGliaClientBeanName(), this.getGliaClientBeanClass());
                clientPool = new ClientPool(clientPoolFactory);
                System.out.println("Client pool RUN !!!");
//            }
        }
        System.out.println("Client pool already initialized");
    }

    protected void localInit(){
        this.initPool();
        this.proxyFactoryPool = ProxyFactoryPool.getInstance();
    }

    protected void clientFullReconnect() throws Exception {
        long beginTime = System.currentTimeMillis();

        clientPool.clear();
        clientPool.close();
        clientPool = null;
        Thread.sleep(100);

        this.initPool();
        System.out.println("Reconnected for time:" + (System.currentTimeMillis() - beginTime) + " ms");
    }

    @Override
    public <T> T getProxy(Class<T> interfaceClass) throws Exception {

        if(clientPool == null){
            this.clientFullReconnect();
            if(clientPool == null){
                throw new Exception("Glia client is not running");
            }
        }

        if(this.proxyFactoryPool == null){
            this.clientFullReconnect();
            if(this.proxyFactoryPool == null){
                throw new Exception("Could not get proxyFactory for " + interfaceClass);
            }
        }

        T object = null;

        try{

            System.out.println("Going to create new newProxyInstance from proxyFactory" +this.proxyFactoryPool);
            System.out.println("Client pool is:" + clientPool);

            //object = (T)this.proxyFactory.newProxyInstance(interfaceClass);
            object = (T)this.proxyFactoryPool.newProxyInstance(this.clientPool, interfaceClass);

        }catch(Throwable th){
            // com.reversemind.glia.proxy.ProxySendException: =GLIA= Could not to send data into server: - let's reconnect
            Throwable throwableLocal = th.getCause();

            System.out.println("some troubles with sending data to the server let's reconnect");

            if(throwableLocal.getClass().equals(ProxySendException.class)){
                System.out.println("detected ProxySendException:" + throwableLocal.getMessage());
                this.clientFullReconnect();
                //object = (T)this.proxyFactory.newProxyInstance(interfaceClass);
                System.out.println("Client pool is:" + clientPool);
                object = (T)this.proxyFactoryPool.newProxyInstance(this.clientPool, interfaceClass);
            }

            if(throwableLocal.getCause().getClass().equals(Exception.class)){
                throw new Exception("Could not to get proxy or send data to server");
            }

        }
        return object;
    }


}
