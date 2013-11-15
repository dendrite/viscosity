package com.reversemind.glia.other.jndi;

import cluster.IAddressSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;

/**
 *
 */
public class SpringRemoteEjb implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(SpringRemoteEjb.class);

    public static void main(String... args) {

        // Still some trouble with Spring + EJB

        // DOC - https://docs.jboss.org/author/display/AS72/Remote+EJB+invocations+via+JNDI+-+EJB+client+API+or+remote-naming+project
        // DOC - https://docs.jboss.org/author/display/AS72/EJB+invocations+from+a+remote+client+using+JNDI
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring-jndi.xml");


        final IAddressSearch addressSearch = (IAddressSearch) context.getBean("beanAddressSearch");

        LOG.debug("" + addressSearch.doSearch("Moscow"));

        //No Spring just POJO

    }

}
