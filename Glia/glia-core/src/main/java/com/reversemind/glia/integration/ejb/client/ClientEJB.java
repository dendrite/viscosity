package com.reversemind.glia.integration.ejb.client;

import com.reversemind.glia.client.ClientPool;
import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.client.GliaClientServerDiscovery;
import com.reversemind.glia.client.IGliaClient;
import com.reversemind.glia.proxy.ProxyFactory;
import com.reversemind.glia.proxy.ProxySendException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import java.io.Serializable;
import java.util.Date;

/**
 * Date: 5/22/13
 * Time: 4:16 PM
 *
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
public class ClientEJB extends AbstractClientEJB implements IClientEJB, Serializable  {

    /**
     *
     * Name look at glia-client-context.xml
     *
         <bean id="gliaClientServerDiscovery" class="com.reversemind.glia.client.GliaClientServerDiscovery" scope="prototype">
             <constructor-arg index="0" value="${glia.client.zookeeper.connection}" />
             <constructor-arg index="1" value="${glia.client.zookeeper.base.path}" />
             <constructor-arg index="2" value="${glia.client.service.name}" />
             <constructor-arg index="3" value="${glia.client.timeout}" />
             <constructor-arg index="4" ref="selectorStrategy" />
         </bean>


         <bean id="gliaClient" class="com.reversemind.glia.client.GliaClient" scope="prototype">
             <constructor-arg index="0" value="${glia.client.service.host}" />
             <constructor-arg index="1" value="${glia.client.service.port}" />
         </bean>
     *
     * @return
     */
    public String getGliaClientBeanName(){
        return "gliaClient";
    }

    public Class getGliaClientBeanClass(){
        return GliaClient.class;
    }

}
