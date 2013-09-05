package ejb.server;

import ejb.zookeeper.RunZookeeper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 */
@Singleton
@Startup
public class AutoStartZookeeper {

    @PostConstruct
    public void init(){
        RunZookeeper.start();
    }

    @PreDestroy
    public void shutdown(){
        RunZookeeper.stop();
    }

}
