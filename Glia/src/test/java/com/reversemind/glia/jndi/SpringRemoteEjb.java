package com.reversemind.glia.jndi;

import com.reversemind.glia.cluster.IAddressSearch;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;

/**
 * Date: 4/25/13
 * Time: 11:06 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class SpringRemoteEjb implements Serializable {

    public static void main(String... args) {

        // Still some trouble with Spring + EJB

        // DOC - https://docs.jboss.org/author/display/AS72/Remote+EJB+invocations+via+JNDI+-+EJB+client+API+or+remote-naming+project
        // DOC - https://docs.jboss.org/author/display/AS72/EJB+invocations+from+a+remote+client+using+JNDI
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring-jndi.xml");


        final IAddressSearch addressSearch = (IAddressSearch)context.getBean("beanAddressSearch");

        System.out.println(addressSearch.doSearch("Чонгарский"));

        //No Spring just POJO

    }

}
