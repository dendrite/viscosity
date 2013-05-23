package test;

import com.reversemind.glia.server.GliaServerFactory;
import com.reversemind.glia.server.IGliaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;

/**
 * Date: 5/22/13
 * Time: 6:38 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class go implements Serializable {

    public static void main(String... args) {
        System.setProperty("curator-log-events", "true");

        IGliaServer server;

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/glia-server-context.xml");
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


        server = builderAdvertiser.build();

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

        System.out.println("Server started");
    }

}
