package com.reversemind.glia.other.spring;

import com.reversemind.glia.client.GliaClientServerDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;

/**
 * Date: 5/17/13
 * Time: 8:51 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaClientSpringContextLoader implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(GliaClientSpringContextLoader.class);

    public static void main(String... args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/glia-client-context.xml");

        GliaClientServerDiscovery client = applicationContext.getBean("clientServerDiscovery", GliaClientServerDiscovery.class);

        client.start();

        Thread.sleep(40000);

        client.shutdown();

    }

}
