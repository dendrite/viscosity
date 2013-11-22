package com.reversemind.test.CQL3;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.IntegerSerializer;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 11/19/13
 * Time: 9:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateTable2 {


    public static void main(String... args) throws Exception{

        Keyspace keyspace = GoCreate.getKeySpace("space2");

        ColumnFamily<Integer, Integer> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                IntegerSerializer.get(),
                IntegerSerializer.get());

        OperationResult<CqlResult<Integer, Integer>> result = keyspace
                .prepareQuery(CQL3_CF)
                .withCql("CREATE TABLE mapper (id uuid, key_mapper varchar, value_mapper varchar, time timestamp, PRIMARY KEY (id, key_mapper,value_mapper));")
                .execute();
        // CREATE INDEX idx_user ON users (user_id);

        System.out.println(result.getResult());

    }

}
