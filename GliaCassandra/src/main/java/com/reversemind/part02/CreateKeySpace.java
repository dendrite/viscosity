package com.reversemind.part02;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;

import java.io.Serializable;

/**
 * Date: 5/8/13
 * Time: 12:28 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class CreateKeySpace implements Serializable {

    /**
     *
     * @param args
     */
    public static void main(String... args) throws ConnectionException {

        // CQL
        // CREATE KEYSPACE Excelsior WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

        Keyspace keyspace = GoCreate.getKeySpace("TWITTER");

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
