package com.reversemind.test.TwitterUsers;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.Rows;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

/**
 *
 */
public class ReadDataByClass {


    public static void main(String... args) throws ConnectionException {

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);
        ColumnFamily<Integer, String> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                IntegerSerializer.get(),
                StringSerializer.get());

        OperationResult<Rows<Integer, String>> result;
        result = keyspace.prepareQuery(CQL3_CF)
                .searchWithIndex()
                .setRowLimit(100)
                .addExpression()
                .whereColumn("name").equals().value("name10")
                .execute();

    }
}
