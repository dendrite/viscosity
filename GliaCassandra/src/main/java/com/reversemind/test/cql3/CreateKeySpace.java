package com.reversemind.test.cql3;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.LongSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

/**
 *
 *
 */
public class CreateKeySpace {

    public static void main(String... args) throws ConnectionException {

        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster("TestCluster")
                .forKeyspace("T2")
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                        .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
                        .setCqlVersion("3.0.0")
                        .setTargetCassandraVersion("1.2")
                )
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MinderConnectionPool")
                        .setPort(9160)
                        .setMaxConnsPerHost(1)
                        .setSeeds("127.0.0.1")
                )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        context.start();
        Keyspace keyspace = context.getClient();


        ColumnFamily<Long, String> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                LongSerializer.get(),
                StringSerializer.get());

        OperationResult<CqlResult<Long, String>> result;
        result = keyspace
                .prepareQuery(CQL3_CF)
                .withCql("CREATE KEYSPACE demodb WITH REPLICATION = {'class' : 'SimpleStrategy','replication_factor': 1};")
                .execute();

        System.out.println(result);
    }

}
