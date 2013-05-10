package com.reversemind.cassandra.mapper;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;

import java.util.List;

/**
 *
 */
public class ReadHistoryData {

    public static void main(String... args){

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);

        EntityManager<TUserHistory, Long> persisterHistory = new DefaultEntityManager.Builder<TUserHistory, Long>()
                .withEntityType(TUserHistory.class)
                .withKeyspace(keyspace)
                .build();

        List<TUserHistory> list = persisterHistory.find("SELECT * FROM tuserhistory;".toLowerCase());
        for(TUserHistory history: list){
            System.out.println(history);
        }

        System.out.println("\n\n");
        list = persisterHistory.find("SELECT * FROM tuserhistory where 'user.id' = 100;".toLowerCase());
        for(TUserHistory history: list){
            System.out.println(history);
        }

    }

}
