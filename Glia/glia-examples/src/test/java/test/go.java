package test;

import com.reversemind.glia.server.GliaServerFactory;
import com.reversemind.glia.server.IGliaServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(go.class);

    public static void main(String... args) {
        System.setProperty("curator-log-events", "true");

        IGliaServer server;

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/glia-server-context.xml");
        GliaServerFactory.Builder builderAdvertiser = applicationContext.getBean("serverBuilder", GliaServerFactory.Builder.class);

        LOG.debug("--------------------------------------------------------");
        LOG.debug("Builder properties:");
        LOG.debug("Name:" + builderAdvertiser.getName());
        LOG.debug("Instance Name:" + builderAdvertiser.getInstanceName());
        LOG.debug("port:" + builderAdvertiser.getPort());
        LOG.debug("isAutoSelectPort:" + builderAdvertiser.isAutoSelectPort());

        LOG.debug("Type:" + builderAdvertiser.getType());

        LOG.debug("Zookeeper connection string:" + builderAdvertiser.getZookeeperHosts());
        LOG.debug("Zookeeper base path:" + builderAdvertiser.getServiceBasePath());


        server = builderAdvertiser.build();

        LOG.debug("\n\n");
        LOG.debug("--------------------------------------------------------");
        LOG.debug("After server initialization - properties");
        LOG.debug("\n");
        LOG.debug("Server properties:");
        LOG.debug("......");
        LOG.debug("Name:" + server.getName());
        LOG.debug("Instance Name:" + server.getInstanceName());
        LOG.debug("port:" + server.getPort());

        server.start();

        LOG.debug("Server started");
    }

}
