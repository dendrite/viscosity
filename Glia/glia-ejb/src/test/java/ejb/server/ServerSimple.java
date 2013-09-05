package ejb.server;

import com.reversemind.glia.integration.ejb.server.ServerEJBAdvertiser;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.Serializable;

/**
 *
 */
@Startup
@Singleton
public class ServerSimple extends ServerEJBAdvertiser implements Serializable {

    @PostConstruct
    public void init(){
        try {
            // Cause zookeeper should be started earlier
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.init();
    }

    @Override
    public String getContextXML(){
        return "META-INF/glia-server-context.xml";
    }
}
