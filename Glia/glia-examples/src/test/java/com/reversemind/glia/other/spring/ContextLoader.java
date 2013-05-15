package com.reversemind.glia.other.spring;

import com.reversemind.glia.server.GliaServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;

/**
 *
 */
public class ContextLoader implements Serializable {

    public static void main(String... args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/glia-context.xml");




        GliaServerFactory factory = (GliaServerFactory)context.getBean("gliaFactory");

        System.out.println(factory);


//        GliaServerFactory.Builder builder = (GliaServerFactory.Builder)context.getBean("gliaBuilder2");
//
//        System.out.println(builder.port());



//        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/glia-context.xml");
//
//        IGliaPayloadProcessor payloadProcessor = (IGliaPayloadProcessor) context.getBean("serverPayloadProcessor");
//
//        Map<Class, Class> map = payloadProcessor.getPojoMap();
//        Set<Class> set = map.keySet();
//        for (Class clazz : set) {
//            System.out.println(clazz.getCanonicalName() + "|" + map.get(clazz).getCanonicalName());
//        }
//
//        GliaServer server = (GliaServer) context.getBean("gliaServer");
//        if (server != null) {
//            System.out.println("!!!!");
//        }
//
//        server.start();

    }

}
