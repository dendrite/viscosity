package com.reversemind.test.TwitterUsers;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WriteData {

    public static void main(String... args){

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);

        EntityManager<TUser, Long> entityPersister = new DefaultEntityManager.Builder<TUser, Long>()
                .withEntityType(TUser.class)
                .withKeyspace(keyspace)
                .build();

        TUser tUser = new TUser(1,"name1", "screenname");
        entityPersister.put(tUser);

        List<TUser> list = new ArrayList<TUser>();
        for(int i=0; i<20; i++){
            list.add(new TUser(i,"name" + i, "screenname" + i));
        }

        entityPersister.put(list);

        TUser user = entityPersister.get(1000L);
        System.out.println(user);
    }

}
