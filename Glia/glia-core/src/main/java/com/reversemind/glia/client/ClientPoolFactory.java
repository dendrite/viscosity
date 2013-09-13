package com.reversemind.glia.client;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class ClientPoolFactory extends BasePoolableObjectFactory<IGliaClient> {

    private String contextXML;
    private String beanName;
    private Class<? extends IGliaClient> clientClazz;
    private int poolSize;

    public ClientPoolFactory(String contextXML, String beanName, Class<? extends IGliaClient> clientClazz) {
        this.contextXML = contextXML;
        this.beanName = beanName;
        this.clientClazz = clientClazz;
    }

    @Override
    public IGliaClient makeObject() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(this.contextXML);
        IGliaClient client = applicationContext.getBean(this.beanName, this.clientClazz);
        if(client == null){
            throw new RuntimeException("Could not create gliaClient for beanName:" + beanName + " and Class:" + this.clientClazz + " and contextName:" + this.contextXML);
        }
        client.start();
        return client;
    }

//    public void destroyObject(IGliaClient client) throws Exception {
//        System.out.println("Going to destroy client:" + client);
//        if(client != null && client.isOccupied() == false){
//            client.shutdown();
//            client = null;
//            System.out.println("Client destroyed");
//        }
//    }

    public void activateObject(IGliaClient client) throws Exception {

    }

    public void passivateObject(IGliaClient client) throws Exception {

    }

}
