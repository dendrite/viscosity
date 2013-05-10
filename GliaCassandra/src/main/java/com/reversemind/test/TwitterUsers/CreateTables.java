package com.reversemind.test.TwitterUsers;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;

/**
 *
 */
public class CreateTables {

    public static void main(String... args){

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);

        EntityManager<TUser, Long> entityPersister = new DefaultEntityManager.Builder<TUser, Long>()
                .withEntityType(TUser.class)
                .withKeyspace(keyspace)
                .build();

        entityPersister.createStorage(null);

    }

}
