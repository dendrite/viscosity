package com.reversemind.glia.integration.ejb.server;

import com.reversemind.glia.server.GliaServerFactory;
import com.reversemind.glia.server.IGliaServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import java.io.Serializable;

/**
 * Date: 5/17/13
 * Time: 8:00 AM
 *
 * @author konilovsky
 * @since 1.0
 */
@Singleton
public class ServerEJBAdvertiser implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ServerEJBAdvertiser.class);

    private IGliaServer server;

    public IGliaServer getServer() {
        return server;
    }

    @PostConstruct
    public void init() {

        //https://issues.apache.org/jira/browse/ZOOKEEPER-1554
        //System.setProperty("java.security.auth.login.config","/opt/zookeeper/conf/jaas.conf");
//        System.setProperty("java.security.auth.login.config","/opt/zookeeper/conf");
        System.setProperty("curator-log-events", "true");

        LOG.debug("this.getContextXML():" + this.getContextXML());
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(this.getContextXML());

        GliaServerFactory.Builder builderAdvertiser = applicationContext.getBean("serverBuilderAdvertiser", GliaServerFactory.Builder.class);

        LOG.info("--------------------------------------------------------");
        LOG.info("Builder properties:");
        LOG.info("Name:" + builderAdvertiser.getName());
        LOG.info("Instance Name:" + builderAdvertiser.getInstanceName());
        LOG.info("port:" + builderAdvertiser.getPort());
        LOG.info("isAutoSelectPort:" + builderAdvertiser.isAutoSelectPort());
        LOG.info("Type:" + builderAdvertiser.getType());
        LOG.info("Zookeeper connection string:" + builderAdvertiser.getZookeeperHosts());
        LOG.info("Zookeeper base path:" + builderAdvertiser.getServiceBasePath());

        this.server = builderAdvertiser.build();

        LOG.info("\n\n");
        LOG.info("--------------------------------------------------------");
        LOG.info("After server initialization - properties");
        LOG.info("\n");
        LOG.info("Server properties:");
        LOG.info("......");
        LOG.info("Name:" + this.server.getName());
        LOG.info("Instance Name:" + this.server.getInstanceName());
        LOG.info("port:" + this.server.getPort());

        this.server.start();

        LOG.warn("Server started");
    }

    @PreDestroy
    public void destroy() {
        if (this.server != null) {
            //server SHUTDOWN
            this.server.shutdown();
            LOG.warn("SERVER SHUTDOWN");
        }
    }

    public String getContextXML() {
        return "META-INF/glia-server-context.xml";
    }

}
