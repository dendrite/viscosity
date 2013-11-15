package ejb.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class RunZookeeper {

    private static final Logger LOG = LoggerFactory.getLogger(RunZookeeper.class);

    public static void start(){
        String port = "2181";
        String dataDirectory = System.getProperty("java.io.tmpdir");

        LocalZookeeper.start(new String[]{port, dataDirectory});
        LOG.info(dataDirectory);
    }

    public static void stop(){
        LocalZookeeper.stop();
    }

    public static void main(String... args) throws InterruptedException {
        String port = "2181";
        String dataDirectory = System.getProperty("java.io.tmpdir");

        LocalZookeeper.start(new String[]{port, dataDirectory});
        LOG.info(dataDirectory);

        Thread.sleep(60000);
        LocalZookeeper.stop();
    }

}
