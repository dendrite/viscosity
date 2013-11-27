package com.reversemind.test.CQL3;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.util.TimeUUIDUtils;

import java.util.Date;
import java.util.UUID;

/**
 *
 */
public class WriteEvents {

    static ColumnFamily<String, String> CQL3_CF = ColumnFamily.newColumnFamily(
            "Cql3CF",
            StringSerializer.get(),
            StringSerializer.get());

    final static String INSERT_EVENT_STATEMENT = "INSERT INTO events " +
            " (" +
            "   event_id, " +
            "   eventType, " +
            "   country, " +
            "   user_id, " +
            "   userLevel" +
            " ) " +
            " VALUES " +
            " (?, ?, ?, ?, ?);";

    public static void main(String... args) throws ConnectionException, InterruptedException {
        Keyspace keyspace = GoCreate.getKeySpace("jetter");

        OperationResult<CqlResult<UUID, String>> result;


        UUID timeUUID = TimeUUIDUtils.getMicrosTimeUUID(new Date().getTime());
        System.out.println("UUID time:" + timeUUID.toString());
        int eventType  = 5;
        int country = 2;
        UUID user_id = UUID.randomUUID(); // UUID.fromString("b392d3ae-041c-4070-a84a-7eda534cc8c0");//
        int userLevel = 100;

        System.out.println("timeUUID:" + timeUUID);

        insertRow(keyspace, timeUUID, ""+5, ""+2, UUID.randomUUID(), ""+100);
        insertRow(keyspace, timeUUID, ""+5, ""+1, UUID.randomUUID(), ""+100);
        insertRow(keyspace, timeUUID, ""+5, ""+2, UUID.randomUUID(), ""+101);
        insertRow(keyspace, timeUUID, ""+5, ""+3, UUID.randomUUID(), ""+101);
        insertRow(keyspace, timeUUID, ""+5, ""+3, UUID.randomUUID(), ""+105);
        insertRow(keyspace, timeUUID, ""+5, ""+5, UUID.randomUUID(), ""+105);


    }


    //        .withCql("CREATE TABLE events (" +
//                " event_id timeuuid, " +
//                " eventType varchar, " +
//                " country varchar, " +
//                " user_id uuid, " +
//                " userLevel varchar, " +
//                " PRIMARY KEY ((event_id, eventType, country), user_id));")

    public static OperationResult<CqlResult<String, String>> insertRow(
            Keyspace keyspace,

            UUID timeUUID,
            String eventType,
            String country,
            UUID userId,
            String userLevel) throws ConnectionException {
        return keyspace
                .prepareQuery(CQL3_CF)
                .withCql(INSERT_EVENT_STATEMENT)
                .asPreparedStatement()

                .withUUIDValue(timeUUID)
                .withStringValue(eventType)
                .withStringValue(country)
                .withUUIDValue(userId)
                .withStringValue(userLevel)
                .execute();
    }

}
