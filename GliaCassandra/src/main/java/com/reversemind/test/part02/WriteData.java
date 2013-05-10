package com.reversemind.test.part02;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

import java.io.Serializable;

/**
 * Date: 5/8/13
 * Time: 1:42 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class WriteData implements Serializable {

    public static void main(String... args) throws Exception {

        Keyspace keyspace = GoCreate.getKeySpace("TWITTER");

        ColumnFamily<Integer, String> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                IntegerSerializer.get(),
                StringSerializer.get());

        OperationResult<CqlResult<Integer, String>> result =
                keyspace.prepareQuery(CQL3_CF)
                .withCql("INSERT INTO users (user_id, screenName, otherParameters) VALUES (-1, 'SimpleName', 'SimpleParameters');")
                .execute();


    }

}
