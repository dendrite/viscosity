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

import java.util.Date;
import java.util.UUID;

/**
 *
 */
public class WriteTimeData {

    static ColumnFamily<String, String> CQL3_CF = ColumnFamily.newColumnFamily(
            "Cql3CF",
            StringSerializer.get(),
            StringSerializer.get());

    final static String INSERT_USER_STATEMENT = "INSERT INTO eventRecords " +
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

        Keyspace keyspace = GoCreate.getKeySpace("space2");



//
//        .withCql("CREATE TABLE eventRecords (" +
//                " event_id timeuuid, " +
//                " eventType int, " +
//                " country int, " +
//                " user_id UUID, " +
//                " userLevel int, " +
//                " PRIMARY KEY (event_id, eventType, country));")



        OperationResult<CqlResult<UUID, String>> result;

        UUID timeUUID = TimeUUIDUtils.getMicrosTimeUUID(new Date().getTime());
        System.out.println("UUID time:" + timeUUID.toString());
        int eventType  = 5;
        int country = 2;
        UUID user_id = UUID.randomUUID(); // UUID.fromString("b392d3ae-041c-4070-a84a-7eda534cc8c0");//
        int userLevel = 100;

        System.out.println("timeUUID:" + timeUUID);

        putRow(keyspace, timeUUID, 5, 2, UUID.randomUUID(), 100);
        putRow(keyspace, timeUUID, 5, 1, UUID.randomUUID(), 100);
        putRow(keyspace, timeUUID, 5, 2, UUID.randomUUID(), 101);
        putRow(keyspace, timeUUID, 5, 3, UUID.randomUUID(), 101);
        putRow(keyspace, timeUUID, 5, 3, UUID.randomUUID(), 105);
        putRow(keyspace, timeUUID, 5, 5, UUID.randomUUID(), 105);

        long bT = System.currentTimeMillis();
        System.out.println("----");
        for(int j=0; j<5; j++){

            Long tt = new Date().getTime();
            timeUUID = TimeUUIDUtils.getTimeUUID(tt);
            System.out.println("UUID time:" + timeUUID.toString() + " -" + tt + "  time:" + new Date(tt));

            for(int i=0; i<5; i++){
                // 79d71c4e-2a6b-11b2-be1c-b870f4f31ee4
               // putRow(keyspace, UUID.fromString("79d71c4e-2a6b-11b2-be1c-b870f4f31ee4"), 5, 10, UUID.randomUUID(), 105);
                putRow(keyspace, timeUUID, 5, 10, UUID.randomUUID(), 105);
            }
            Thread.sleep(2000);
        }

        System.out.println("End:" + (System.currentTimeMillis() - bT));



//        cqlsh:space2> select * from eventRecords where event_id = 'TTT2' and eventtype='5' and country=1 and userLevel=105;
//        cqlsh:space2> select * from eventRecords where event_id = 'TTT2' and eventtype='5' and country=1 and userLevel=101;



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


    public static OperationResult<CqlResult<String, String>> putRow(Keyspace keyspace, UUID timeUUID, int eventType, int country, UUID userId, int userLevel) throws ConnectionException {
        return keyspace
                .prepareQuery(CQL3_CF)
                .withCql(INSERT_USER_STATEMENT)
                .asPreparedStatement()

                .withUUIDValue(timeUUID)
                .withIntegerValue(eventType)
                .withIntegerValue(country)
                .withUUIDValue(userId)
                .withIntegerValue(userLevel)
                .execute();
    }

}
