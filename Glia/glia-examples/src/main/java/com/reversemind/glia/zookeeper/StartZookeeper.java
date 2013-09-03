package com.reversemind.glia.zookeeper;

/**
 *
 */
public class StartZookeeper {

    public static void main(String... args) throws InterruptedException {

        String port = "2181";
        String dataDirectory = "c:\\Zookeeper\\TEMP_DATA\\";//System.getProperty("java.io.tmpdir");

        LocalTestZookeeper.start(new String[]{port, dataDirectory});
        System.out.println(dataDirectory);

        Thread.sleep(60000);
        LocalTestZookeeper.stop();

    }

}
