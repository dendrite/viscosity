package ejb.zookeeper;

/**
 *
 */
public class RunZookeeper {

    public static void start(){
        String port = "2181";
        String dataDirectory = System.getProperty("java.io.tmpdir");

        LocalZookeeper.start(new String[]{port, dataDirectory});
        System.out.println(dataDirectory);
    }

    public static void stop(){
        LocalZookeeper.stop();
    }

    public static void main(String... args) throws InterruptedException {
        String port = "2181";
        String dataDirectory = System.getProperty("java.io.tmpdir");

        LocalZookeeper.start(new String[]{port, dataDirectory});
        System.out.println(dataDirectory);

        Thread.sleep(60000);
        LocalZookeeper.stop();
    }

}
