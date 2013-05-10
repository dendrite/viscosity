package com.reversemind.cassandra.mapper;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WriteData {


    public static void main(String... args) throws ConnectionException {

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);


        EntityManager<TUserHistory, Long> persisterHistory = new DefaultEntityManager.Builder<TUserHistory, Long>()
                .withEntityType(TUserHistory.class)
                .withKeyspace(keyspace)
                .build();




        List<TUserHistory> list = new ArrayList<TUserHistory>();
        list.add(new TUserHistory(1,new TTUser(100, "name_history", "SN_HISTORY"), 100L));
        list.add(new TUserHistory(2,new TTUser(100, "name_history", "SN_HISTORY_02"), 200L));

        list.add(new TUserHistory(3,new TTUser(200, "222", "SN_222"), 100L));
        list.add(new TUserHistory(4,new TTUser(300, "333", "SN_333"), 100L));
        persisterHistory.put(list);
    }

}
