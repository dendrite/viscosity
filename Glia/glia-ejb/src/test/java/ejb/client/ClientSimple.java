package ejb.client;

import com.reversemind.glia.integration.ejb.client.ClientEJBDiscovery;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.io.Serializable;

/**
 *
 */
//@Singleton
//@Startup
@Stateless
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

//    @Lock(LockType.READ)
    @Override
    public <T> T getProxy(Class<T> interfaceClass) throws Exception {
//        super.localInit();
        return super.getProxy(interfaceClass);
    }

}
