package com.reversemind.test.CQL3;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;
import com.netflix.astyanax.util.TimeUUIDUtils;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dendrite
 * Date: 24.11.13
 * Time: 2:21
 * To change this template use File | Settings | File Templates.
 */
public class WriteTimeData {

    public static void main(String... args) throws ConnectionException {

        Keyspace keyspace = GoCreate.getKeySpace("space2");

        ColumnFamily<String, String> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                StringSerializer.get(),
                StringSerializer.get());

//
//        .withCql("CREATE TABLE eventRecords (" +
//                " event_id timeuuid, " +
//                " eventType int, " +
//                " country int, " +
//                " user_id UUID, " +
//                " userLevel int, " +
//                " PRIMARY KEY (event_id, eventType, country));")

        final String INSERT_USER_STATEMENT = "INSERT INTO eventRecords " +
                " (" +
                "   event_id, " +
                "   eventType, " +
                "   country, " +
                "   user_id, " +
                "   userLevel" +
                " ) " +
                " VALUES " +
                " (?, ?, ?, ?, ?);";

        OperationResult<CqlResult<String, String>> result;

        UUID timeUUID = TimeUUIDUtils.getMicrosTimeUUID(1000L);

        int eventType  = 1;
        int country = 2;
        UUID user_id = UUID.randomUUID(); // UUID.fromString("b392d3ae-041c-4070-a84a-7eda534cc8c0");//
        int userLevel = 101;

        System.out.println("timeUUID:" + timeUUID);


            result = keyspace
                    .prepareQuery(CQL3_CF)
                    .withCql(INSERT_USER_STATEMENT)
                    .asPreparedStatement()

                    .withStringValue(timeUUID.toString())
                    .withStringValue("" + eventType)
                    .withIntegerValue(country)
                    .withStringValue(user_id.toString())
                    .withIntegerValue(userLevel)
                    .execute();



//        for(int i=0; i<1000; i++){
//            result = keyspace
//                    .prepareQuery(CQL3_CF)
//                    .withCql(INSERT_USER_STATEMENT)
//                    .asPreparedStatement()
//                    .withLongValue(1L)
//                    .withLongValue(i * 10)
//                    .withLongValue(i * 10)
//                    .execute();
//        }

    }

}
