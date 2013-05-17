package com.reversemind.glia.test.spring;

import com.reversemind.glia.server.GliaServer;
import com.reversemind.glia.server.IGliaPayloadProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Date: 4/25/13
 * Time: 4:13 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class RunServerFromSpringContext implements Serializable {

    public static void main(String... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/glia-server-context.xml");

        IGliaPayloadProcessor payloadProcessor = (IGliaPayloadProcessor) context.getBean("serverPayloadProcessor");

        Map<Class, Class> map = payloadProcessor.getPojoMap();
        Set<Class> set = map.keySet();
        for (Class clazz : set) {
            System.out.println(clazz.getCanonicalName() + "|" + map.get(clazz).getCanonicalName());
        }

        GliaServer server = (GliaServer) context.getBean("gliaServer");
        if (server != null) {
            System.out.println("!!!!");
        }

        server.start();
    }

}
