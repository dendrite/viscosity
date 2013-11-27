package com.reversemind.storm.jetter.cassandra;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;


/**
 *
 */
public class CreateKeySpace {

    public static void main(String... args) throws ConnectionException {
        Keyspace keyspace = CassandraSettings.getKeySpace();

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
