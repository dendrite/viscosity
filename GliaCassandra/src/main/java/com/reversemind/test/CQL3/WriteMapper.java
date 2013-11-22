package com.reversemind.test.CQL3;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.IntegerSerializer;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 11/19/13
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class WriteMapper {

    public static void main(String... args) throws ConnectionException {

        Keyspace keyspace = GoCreate.getKeySpace("space2");

        ColumnFamily<Integer, Integer> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                IntegerSerializer.get(),
                IntegerSerializer.get());

        final String INSERT_MAPPER_STATEMENT = "INSERT INTO mapper (id, key_mapper, value_mapper, time) VALUES (?, ?, ?, ?);";

        OperationResult<CqlResult<Integer, Integer>> result;

        UUID uuid = UUID.randomUUID();
        long tB = System.currentTimeMillis();

        for(int i=0; i<100000; i++){

            if(i % 100 == 0){
                uuid = UUID.randomUUID();
            }
//            System.out.println("uuid:" + uuid);
            result = keyspace
                    .prepareQuery(CQL3_CF)
                    .withCql(INSERT_MAPPER_STATEMENT)
                    .asPreparedStatement()
                    .withUUIDValue(uuid)
                    .withStringValue("key_" + (i > 50000 ? "V1" : "V2" ) )
                    .withStringValue("value_" + 12)
                    .withLongValue(System.currentTimeMillis())
                    .execute();
        }

        System.out.println("END:" + (System.currentTimeMillis() - tB));

    }

}
