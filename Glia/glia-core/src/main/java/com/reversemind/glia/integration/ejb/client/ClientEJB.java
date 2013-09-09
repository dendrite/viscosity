package com.reversemind.glia.integration.ejb.client;

import com.reversemind.glia.client.GliaClient;

import javax.ejb.Stateless;
import java.io.Serializable;

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
@Stateless
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
