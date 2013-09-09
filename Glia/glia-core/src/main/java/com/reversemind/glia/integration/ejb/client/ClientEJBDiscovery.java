package com.reversemind.glia.integration.ejb.client;

import com.reversemind.glia.client.GliaClientServerDiscovery;

import javax.ejb.Stateless;
import java.io.Serializable;

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
@Stateless
public class ClientEJBDiscovery extends AbstractClientEJB implements IClientEJB, Serializable {

    public String getGliaClientBeanName(){
        return "gliaClientServerDiscovery";
    }

    public Class getGliaClientBeanClass(){
        return GliaClientServerDiscovery.class;
    }
}
