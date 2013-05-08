package com.reversemind.part02;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

import java.io.Serializable;

/**
 * Date: 5/8/13
 * Time: 1:45 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class WriteMultiple implements Serializable {

    public static void main(String... args) throws ConnectionException {

        Keyspace keyspace = GoCreate.getKeySpace("TWITTER");

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
