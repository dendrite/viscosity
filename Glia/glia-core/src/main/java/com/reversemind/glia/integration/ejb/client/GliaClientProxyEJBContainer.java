package com.reversemind.glia.integration.ejb.client;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.client.GliaClientServerDiscovery;
import com.reversemind.glia.client.IGliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import java.io.Serializable;
import java.util.Date;

/**
 * Date: 5/17/13
 * Time: 8:29 AM
 *
 * @author konilovsky
 * @since 1.0
 */
@Singleton
public class GliaClientProxyEJBContainer implements IGliaClientProxy, Serializable {

    private GliaClientServerDiscovery client;
    private ProxyFactory proxyFactory = null;

    @PostConstruct
    public void init(){

        // Wait a little -
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/glia-client-context.xml");
        this.client = applicationContext.getBean("clientServerDiscovery", GliaClientServerDiscovery.class);

        try {

            client.start();
            // TODO Logging not System.out.println
            System.out.println("\n\n");
            System.out.println("===============================================");
            System.out.println(" Glia Client started at " + new Date());

            this.proxyFactory = ProxyFactory.getInstance(this.client);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy(){
        if(this.client != null){
            this.client.shutdown();
        }

        if(this.proxyFactory != null){
            this.proxyFactory = null;
        }
    }

    @Override
    public IGliaClient getClient() {
        return this.client;
    }

    @Override
    public Object getProxy(Class interfaceClass) throws Exception {
        if(this.client == null){
            throw new Exception("Glia client is not running");
        }

        if(this.proxyFactory == null){
            throw new Exception("Could not get proxyFactory for ");
        }

        return this.proxyFactory.newProxyInstance(interfaceClass);
    }
}
