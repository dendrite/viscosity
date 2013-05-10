package com.reversemind.test.cassandra;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

import java.io.Serializable;

/**
 * Date: 5/7/13
 * Time: 11:40 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class CreateKeySpaceAndColumnFamily implements Serializable {

    public static final String STATUS = "STATUS";


    public static final String CLUSTER_NAME = "TestCluster";
    public static final String KEY_SPACE = "HugeKeySpace";


    public static ColumnFamily<String, String> CF_STATUS = ColumnFamily
            .newColumnFamily(STATUS,
                    StringSerializer.get(),
                    StringSerializer.get()
            );

    /**
     *
     * @return
     */
    public static Keyspace getKeyspace(){
        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster(CreateKeySpaceAndColumnFamily.CLUSTER_NAME)
                .forKeyspace(CreateKeySpaceAndColumnFamily.KEY_SPACE)
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
        //Keyspace keyspace = context.getClient();
        return context.getClient();
    }

    public static void main(String... args) throws Exception {

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
        Keyspace keyspace = context.getClient();
        // keyspace.dropKeyspace();


        // Using simple strategy
        keyspace.createKeyspace(ImmutableMap.<String, Object>builder()
                .put("strategy_options", ImmutableMap.<String, Object>builder()
                        .put("replication_factor", "1")
                        .build())
                .put("strategy_class",     "SimpleStrategy")
                .build()
        );

        // view keyspace
        System.out.println(keyspace.describeKeyspace().getProperties());
        System.out.println("------------------------------");



        // create CF
        keyspace.createColumnFamily(CF_STATUS, null);
        System.out.println(keyspace.getColumnFamilyProperties(STATUS));

    }

}
