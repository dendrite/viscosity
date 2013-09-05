package ejb.client;

import com.reversemind.glia.integration.ejb.client.ClientEJBDiscovery;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.Serializable;

/**
 *
 */
@Singleton
@Startup
@Local(ClientSimple.class)
public class ClientSimple extends ClientEJBDiscovery implements Serializable {

    /**
     * Should be started even after a zookeeper and server
     */
    @PostConstruct
    public void init(){
        // Wait a little -
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.init();
    }

}
