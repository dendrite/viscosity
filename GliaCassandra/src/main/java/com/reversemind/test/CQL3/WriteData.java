package com.reversemind.test.CQL3;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.reversemind.test.part02.*;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 11/19/13
 * Time: 3:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class WriteData {

    public static void main(String... args) throws ConnectionException {

        Keyspace keyspace = GoCreate.getKeySpace("space2");

        ColumnFamily<Integer, Integer> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                IntegerSerializer.get(),
                IntegerSerializer.get());

        final String INSERT_USER_STATEMENT = "INSERT INTO users (user_id, screenName, otherParameters) VALUES (?, ?, ?);";

        OperationResult<CqlResult<Integer, Integer>> result;


        for(int i=0; i<1000; i++){
            result = keyspace
                    .prepareQuery(CQL3_CF)
                    .withCql(INSERT_USER_STATEMENT)
                    .asPreparedStatement()
                    .withIntegerValue(i)
                    .withIntegerValue(i*10)
                    .withIntegerValue(i*10)
                    .execute();
        }

    }

}
