package com.reversemind.test.TwitterUsers;


import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

/**
 *
 */
public class Minder {

    public static final String CLUSTER_NAME = "MinderCluster";
    public static final String TWITTER_KEY_SPACE = "TWITTER";
    public static final String SEEDS = "176.9.124.53:9160,176.9.59.140:9160,176.9.124.47:9160";

    public static Keyspace getKeySpace(String keySpaceName){
        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster(CLUSTER_NAME)
                .forKeyspace(keySpaceName)
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                        .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
//                        .setCqlVersion("3.0.0")
//                        .setTargetCassandraVersion("1.2")
                )
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MinderConnectionPool")
                        .setPort(9160)
                        .setMaxConnsPerHost(1)
                        .setSeeds(SEEDS)
                )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        context.start();
        return context.getClient();
    }

    public static void main(String... args){

    }

}
