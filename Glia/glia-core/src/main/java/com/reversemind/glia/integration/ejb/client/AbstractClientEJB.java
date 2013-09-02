package com.reversemind.glia.integration.ejb.client;

import com.reversemind.glia.client.GliaClientServerDiscovery;
import com.reversemind.glia.client.IGliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.proxy.ProxySendException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.util.LocaleServiceProviderPool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class AbstractClientEJB implements IClientEJB, Serializable {

    protected IGliaClient client;
    protected ProxyFactory proxyFactory = null;

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
        if(this.client != null){
            this.client.shutdown();
        }

        if(this.proxyFactory != null){
            this.proxyFactory = null;
        }

        this.client = null;
        this.proxyFactory = null;
    }

    protected void localInit(){
        System.out.println("Going to use GliaClient context:" + this.getContextXML());
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(this.getContextXML());
        this.client = applicationContext.getBean("gliaClientServerDiscovery", GliaClientServerDiscovery.class);

        if(this.client == null){
            throw new RuntimeException("Could not construct client from properties");
        }
        try {

            this.client.start();
            // TODO Logging not System.out.println
            System.out.println("\n\n");
            System.out.println("===============================================");
            System.out.println(" Glia Client started at " + new Date());

            this.proxyFactory = ProxyFactory.getInstance(this.client);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void clientFullReconnect() throws Exception {
        long beginTime = System.currentTimeMillis();
        this.destroy();

        if(this.client != null){
            this.client.shutdown();
        }
        this.proxyFactory = null;

        this.localInit();

        Thread.sleep(100);
        this.client.restart();

        System.out.println("Reconnected for time:" + (System.currentTimeMillis() - beginTime) + " ms");
    }

    @Override
    public <T> T getProxy(Class<T> interfaceClass) throws Exception {

        if(this.client == null | !this.client.isRunning()){
            this.clientFullReconnect();
            if(this.client == null | !this.client.isRunning()){
                throw new Exception("Glia client is not running");
            }
        }

        if(this.proxyFactory == null){
            this.clientFullReconnect();
            if(this.proxyFactory == null){
                throw new Exception("Could not get proxyFactory for " + interfaceClass);
            }
        }

        T object = null;

        try{
            object = (T)this.proxyFactory.newProxyInstance(interfaceClass);
        }catch(Throwable th){
            // com.reversemind.glia.proxy.ProxySendException: =GLIA= Could not to send data into server: - let's reconnect
            Throwable throwableLocal = th.getCause();

            System.out.println("some troubles with sending data to the server let's reconnect");

            //
            if(throwableLocal.getClass().equals(ProxySendException.class)){
                System.out.println("detected ProxySendException:" + throwableLocal.getMessage());
                this.clientFullReconnect();
                object = (T)this.proxyFactory.newProxyInstance(interfaceClass);
            }

            if(throwableLocal.getCause().getClass().equals(Exception.class)){
                throw new Exception("Could not to get proxy or send data to server");
            }

        }
        return object;
    }

    @Override
    public IGliaClient getClient() {
        return this.client;
    }

    /**
     * Default value is "META-INF/glia-client-context.xml"
     *
     * @return
     */
    @Override
    public String getContextXML() {
        return "META-INF/glia-client-context.xml";
    }
}
