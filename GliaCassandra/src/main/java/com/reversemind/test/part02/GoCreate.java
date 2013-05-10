package com.reversemind.test.part02;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

import java.io.Serializable;

/**
 * Date: 5/8/13
 * Time: 12:10 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GoCreate implements Serializable {

    public static final String CLUSTER_NAME = "TestCluster";
    public static final String KEY_SPACE = "LISTS";

    /**
     *
     * @return
     */
    public static Keyspace getKeySpace(){
        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster(CLUSTER_NAME)
                .forKeyspace(KEY_SPACE)
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                        .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
                        .setCqlVersion("3.0.0")
                        .setTargetCassandraVersion("1.2")
                )
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool")
                        .setPort(9160)
                        .setMaxConnsPerHost(1)
                        .setSeeds("127.0.0.1:9160")
                )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        context.start();
        return context.getClient();
    }

    /**
     *
     * @param keySpaceName
     * @return
     */
    public static Keyspace getKeySpace(String keySpaceName){
        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster(CLUSTER_NAME)
                .forKeyspace(keySpaceName)
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                        .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
                        .setCqlVersion("3.0.0")
                        .setTargetCassandraVersion("1.2")
                )
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool")
                        .setPort(9160)
                        .setMaxConnsPerHost(1)
                        .setSeeds("127.0.0.1:9160")
                )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        context.start();
        return context.getClient();
    }


    public static void main(String... args) throws Exception {

        Keyspace keyspace = getKeySpace();
        // keyspace.dropKeyspace();


        // Using simple strategy
        keyspace.createKeyspace(ImmutableMap.<String, Object>builder()
                .put("strategy_options", ImmutableMap.<String, Object>builder()
                        .put("replication_factor", "1")
                        .build())
                .put("strategy_class",     "SimpleStrategy")
                .build()
        );
    }

}
