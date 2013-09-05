package com.reversemind.glia.integration.ejb.server;

import com.reversemind.glia.server.GliaServerFactory;
import com.reversemind.glia.server.IGliaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import java.io.Serializable;

/**
 * Date: 5/22/13
 * Time: 4:31 PM
 *
 * @author konilovsky
 * @since 1.0
 */
@Singleton
public class ServerEJB implements Serializable {

    private IGliaServer server;

    @PostConstruct
    public void init(){

        //https://issues.apache.org/jira/browse/ZOOKEEPER-1554
        //System.setProperty("java.security.auth.login.config","/opt/zookeeper/conf/jaas.conf");
//        System.setProperty("java.security.auth.login.config","/opt/zookeeper/conf");
        System.setProperty("curator-log-events", "true");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(this.getContextXML());
        GliaServerFactory.Builder builderAdvertiser = applicationContext.getBean("serverBuilder",GliaServerFactory.Builder.class);

        System.out.println("--------------------------------------------------------");
        System.out.println("Builder properties:");
        System.out.println("Name:" + builderAdvertiser.getName());
        System.out.println("Instance Name:" + builderAdvertiser.getInstanceName());
        System.out.println("port:" + builderAdvertiser.getPort());
        System.out.println("isAutoSelectPort:" + builderAdvertiser.isAutoSelectPort());

        System.out.println("Type:" + builderAdvertiser.getType());

        System.out.println("Zookeeper connection string:" + builderAdvertiser.getZookeeperHosts());
        System.out.println("Zookeeper base path:" + builderAdvertiser.getServiceBasePath());


        this.server = builderAdvertiser.build();

        System.out.println("\n\n");
        System.out.println("--------------------------------------------------------");
        System.out.println("After server initialization - properties");
        System.out.println("\n");
        System.out.println("Server properties:");
        System.out.println("......");
        System.out.println("Name:" + this.server.getName());
        System.out.println("Instance Name:" + this.server.getInstanceName());
        System.out.println("port:" + this.server.getPort());

        this.server.start();

        System.out.println("Server started");

    }

    @PreDestroy
    public void destroy(){
        if(this.server != null){
            //server SHUTDOWN
            this.server.shutdown();
            System.out.println("SERVER SHUTDOWN");
        }
    }

    public String getContextXML(){
        return "META-INF/glia-server-context.xml";
    }
}
