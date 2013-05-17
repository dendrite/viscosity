package com.reversemind.glia.other.spring;

import com.reversemind.glia.GliaPayload;
import com.reversemind.glia.server.GliaServerFactory;
import com.reversemind.glia.server.IGliaPayloadProcessor;
import com.reversemind.glia.server.IGliaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;
import java.util.Map;

/**
 *
 */
public class GliaServerSpringContextLoader implements Serializable {

    public static void main(String... args) throws InterruptedException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/glia-server-context.xml");

        GliaServerFactory.Builder builderAdvertiser = applicationContext.getBean("serverBuilderAdvertiser",GliaServerFactory.Builder.class);

        System.out.println("--------------------------------------------------------");
        System.out.println("Builder properties:");
        System.out.println("Name:" + builderAdvertiser.getName());
        System.out.println("Instance Name:" + builderAdvertiser.getInstanceName());
        System.out.println("port:" + builderAdvertiser.getPort());
        System.out.println("isAutoSelectPort:" + builderAdvertiser.isAutoSelectPort());

        System.out.println("Type:" + builderAdvertiser.getType());

        System.out.println("Zookeeper connection string:" + builderAdvertiser.getZookeeperHosts());
        System.out.println("Zookeeper base path:" + builderAdvertiser.getServiceBasePath());







        IGliaServer server = builderAdvertiser.build();

        System.out.println("\n\n");
        System.out.println("--------------------------------------------------------");
        System.out.println("After server initialization - properties");
        System.out.println("\n");
        System.out.println("Server properties:");
        System.out.println("......");
        System.out.println("Name:" + server.getName());
        System.out.println("Instance Name:" + server.getInstanceName());
        System.out.println("port:" + server.getPort());

        server.start();

        Thread.sleep(60000);

        server.shutdown();



        GliaServerFactory.Builder builderSimple = (GliaServerFactory.Builder)applicationContext.getBean("serverBuilderSimple");
        System.out.println(builderSimple.port());

        IGliaServer serverSimple = builderSimple
                .setAutoSelectPort(true)
                .setName("N A M E")
                .setPort(8000)
                .setPayloadWorker(new IGliaPayloadProcessor() {
                    @Override
                    public Map<Class, Class> getPojoMap() {
                        return null;
                    }

                    @Override
                    public void setPojoMap(Map<Class, Class> map) {
                    }

                    @Override
                    public void setEjbMap(Map<Class, String> map) {
                    }

                    @Override
                    public void registerPOJO(Class interfaceClass, Class pojoClass) {
                    }

                    @Override
                    public GliaPayload process(Object gliaPayloadObject) {
                        return null;
                    }
                }).build();


        System.out.println("\n\n");
        System.out.println("--------------------------------------------------------");
        System.out.println("Simple Glia server");
        System.out.println("\n");
        System.out.println("Server properties:");
        System.out.println("......");
        System.out.println("Name:" + serverSimple.getName());
        System.out.println("Instance Name:" + serverSimple.getInstanceName());
        System.out.println("port:" + serverSimple.getPort());

    }

}
