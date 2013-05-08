package com.reversemind.part02;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

import java.io.Serializable;

/**
 * Date: 5/8/13
 * Time: 1:34 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class CreateTables implements Serializable {

    public static void main(String... args) throws Exception{

        Keyspace keyspace = GoCreate.getKeySpace("TWITTER");

        ColumnFamily<Integer, Integer> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                IntegerSerializer.get(),
                IntegerSerializer.get());


        OperationResult<CqlResult<Integer, Integer>> result = keyspace
                .prepareQuery(CQL3_CF)
                .withCql("CREATE TABLE users (user_id int, screenName int, otherParameters int, PRIMARY KEY (user_id, screenName));")
                .execute();
        // CREATE INDEX idx_user ON users (user_id);

        System.out.println(result.getResult());

    }

}
