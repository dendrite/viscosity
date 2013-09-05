package ejb.zookeeper;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;

import java.io.IOException;

/**
 *
 */
public class LocalZookeeper extends ZooKeeperServerMain implements Runnable {

    private static LocalZookeeper zookeeperServer;
    private static ServerConfig serverConfig;

    /**
     * Just need port number usually it's 2181
     * And temp directory
     *
     * @param configPath
     */
    public static void start(String[] configPath) {
        serverConfig = new ServerConfig();
        serverConfig.parse(configPath);
        zookeeperServer = new LocalZookeeper();
        (new Thread(zookeeperServer)).start();
    }

    public static void stop() {
        if(zookeeperServer != null){
            zookeeperServer.shutdown();
        }
    }

    @Override
    public void run() {
        try {
            zookeeperServer.runFromConfig(serverConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
