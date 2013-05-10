package com.reversemind.test.TwitterUsers;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;

import java.util.List;

/**
 *
 *
 */
public class ReadData {

    public static void main(String... args){

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);

        EntityManager<TUser, Long> entityPersister = new DefaultEntityManager.Builder<TUser, Long>()
                .withEntityType(TUser.class)
                .withKeyspace(keyspace)
                .build();

        TUser user = entityPersister.get(10L);

        System.out.println(user);


        List<TUser> list = entityPersister.find("SELECT * FROM tuser where screenName='screenname10';".toLowerCase());
        for(TUser tUser: list){
            System.out.println(tUser);
        }

    }

}
