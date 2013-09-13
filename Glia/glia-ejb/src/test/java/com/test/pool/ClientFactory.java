package com.test.pool;

import com.reversemind.glia.client.GliaClientServerDiscovery;
import com.reversemind.glia.client.IGliaClient;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class ClientFactory extends BasePoolableObjectFactory<IGliaClient> {

    private String contextXML;
    private String beanName;
    private Class<? extends IGliaClient> clientClazz;

    /**
     *
     * @param contextXML
     * @param clientClazz
     * @param beanName
     */
    public ClientFactory(String contextXML, String beanName, Class<? extends IGliaClient> clientClazz) {
        this.contextXML = contextXML;
        this.beanName = beanName;
        this.clientClazz = clientClazz;
    }

    @Override
    public IGliaClient makeObject() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(this.contextXML);
        IGliaClient client = applicationContext.getBean(this.beanName, this.clientClazz);
        client.start();
        return client;
    }

    public void destroyObject(IGliaClient client) throws Exception  {
        if(client != null){
            client.shutdown();
            client = null;
        }
    }

    public void activateObject(IGliaClient client) throws Exception {

    }

    public void passivateObject(IGliaClient client) throws Exception {

    }

}
