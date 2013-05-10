package com.reversemind.test.TwitterUsers;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.LongSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

/**
 *
 */
public class CQLCreate {


    public static void main(String... args) throws Exception {

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);


        ColumnFamily<Long, String> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                LongSerializer.get(),
                StringSerializer.get());

        OperationResult<CqlResult<Long, String>> result;
        result = keyspace
                .prepareQuery(CQL3_CF)
                .withCql("CREATE TABLE TUser (id bigint, name varchar, screenName varchar, PRIMARY KEY (id));")
                .execute();


    }

}
