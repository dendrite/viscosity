package com.reversemind.glia.integration.ejb.client;

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
 * a. Remove pojo and jpa modules
 * Pojo extension
 * <extension module="org.jboss.as.osgi"/>
 * <!-- Remove this line extension module="org.jboss.as.pojo"/-->
 * <extension module="org.jboss.as.remoting"/>
 * And pojo domain
 * <subsystem xmlns="urn:jboss:domain:naming:1.0" />
 * <!--subsystem xmlns="urn:jboss:domain:pojo:1.0" /-->
 * <subsystem xmlns="urn:jboss:domain:osgi:1.0" activation="lazy">
 *
 * @author konilovsky
 * @since 1.0
 */
@Singleton
public class ClientEJBDiscovery implements IClientEJB, Serializable {

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
        this.client = applicationContext.getBean("gliaClientServerDiscovery", GliaClientServerDiscovery.class);

        if(this.client == null){
            throw new RuntimeException("Could not construct client from properties");
        }
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
    public <T> Object getProxy(Class<T> interfaceClass) throws Exception {
        if(this.client == null){
            throw new Exception("Glia client is not running");
        }

        if(this.proxyFactory == null){
            throw new Exception("Could not get proxyFactory for ");
        }

        return (T)this.proxyFactory.newProxyInstance(interfaceClass);
    }
}
