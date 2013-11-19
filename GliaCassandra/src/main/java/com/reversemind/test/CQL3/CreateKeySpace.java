package com.reversemind.test.CQL3;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.reversemind.test.part02.*;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 11/19/13
 * Time: 3:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateKeySpace {

    public static void main(String... args) throws ConnectionException {
        Keyspace keyspace = GoCreate.getKeySpace("space2");

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
