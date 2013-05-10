package com.reversemind.test.TwitterUsers;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.LongSerializer;

/**
 *
 */
public class CQLInsert {

    public static void main(String... args) throws Exception {

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);

        ColumnFamily<Long, Long> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                LongSerializer.get(),
                LongSerializer.get());

        final String INSERT_TUSER_STATEMENT = "INSERT INTO TUser (id, name, screenName) VALUES (?, ?, ?);";


        OperationResult<CqlResult<Long, Long>> result;


        result = keyspace
                .prepareQuery(CQL3_CF)
                .withCql(INSERT_TUSER_STATEMENT)
                .asPreparedStatement()
                .withLongValue(1000L)
                .withStringValue("name_5003")
                .withStringValue("screenName_NEW")
                .execute();
        //System.exit(0);

        for(int i=0; i<10; i++){
            result = keyspace
                    .prepareQuery(CQL3_CF)
                    .withCql(INSERT_TUSER_STATEMENT)
                    .asPreparedStatement()
                    .withLongValue(0L + 500 + i)
                    .withStringValue("name_" + 500 + i)
                    .withStringValue("screenName" + 500 + i)
                    .execute();
        }


    }

}
