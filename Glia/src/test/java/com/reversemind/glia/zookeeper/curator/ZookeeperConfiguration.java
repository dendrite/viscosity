package com.reversemind.glia.zookeeper.curator;

import java.io.Serializable;

/**
 * Date: 4/29/13
 * Time: 11:20 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ZookeeperConfiguration implements Serializable {

    public static final String ZOOKEEPER_CONNECTION = "127.0.0.1:2181";
    public static final String BASE_PATH = "/baloo";

}
