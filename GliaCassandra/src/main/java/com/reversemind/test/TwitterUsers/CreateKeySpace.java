package com.reversemind.test.TwitterUsers;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.Keyspace;

/**
 *
 */
public class CreateKeySpace {

    public static void main(String... args) throws Exception {

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);
        // keyspace.dropKeyspace();


        // Using simple strategy
        keyspace.createKeyspace(ImmutableMap.<String, Object>builder()
                .put("strategy_options", ImmutableMap.<String, Object>builder()
                        .put("replication_factor", "3")
                        .build())
                .put("strategy_class",     "SimpleStrategy")
                .build()
        );
    }

}
