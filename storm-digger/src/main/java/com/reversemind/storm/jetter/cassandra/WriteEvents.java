package com.reversemind.storm.jetter.cassandra;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.util.TimeUUIDUtils;
import com.reversemind.storm.jetter.event.Event;
import com.reversemind.storm.jetter.event.EventElement;
import com.reversemind.storm.jetter.event.EventType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class WriteEvents {

    static ColumnFamily<String, String> CQL3_CF = ColumnFamily.newColumnFamily(
            CassandraSettings.CQL_COLUMN_FAMILY,
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
        Keyspace keyspace = CassandraSettings.getKeySpace();

        OperationResult<CqlResult<UUID, String>> result;

        UUID timeUUID = TimeUUIDUtils.getMicrosTimeUUID(new Date().getTime());
        System.out.println("UUID time:" + timeUUID.toString());

        System.out.println("timeUUID:" + timeUUID);

        insertRow(keyspace, timeUUID, ""+5, ""+2, UUID.randomUUID(), ""+100);
        insertRow(keyspace, timeUUID, "" + 5, "" + 1, UUID.randomUUID(), "" + 100);
        insertRow(keyspace, timeUUID, "" + 5, "" + 2, UUID.randomUUID(), "" + 101);
        insertRow(keyspace, timeUUID, "" + 5, "" + 3, UUID.randomUUID(), "" + 101);
        insertRow(keyspace, timeUUID, "" + 5, "" + 3, UUID.randomUUID(), "" + 105);
        insertRow(keyspace, timeUUID, "" + 5, "" + 5, UUID.randomUUID(), "" + 105);

    }

    public static OperationResult<CqlResult<String, String>> insertRow(Event event) throws ConnectionException{

        String eventType = "-";
        String userLevel = "-";

        List<EventElement> list =  event.getElements();
        if(list != null && list.size()>0){
            eventType = list.get(0).getType().toString();
            if(list.get(0).getType().equals(EventType.CHANGED_LEVEL)){
                userLevel = list.get(0).getValue();
            }
        }

        return CassandraSettings.getKeySpace()
                .prepareQuery(CQL3_CF)
                .withCql(INSERT_EVENT_STATEMENT)
                .asPreparedStatement()

                .withUUIDValue(TimeUUIDUtils.getTimeUUID(event.getTimeStamp()))
                .withStringValue(eventType)
                .withStringValue(event.getUser().getCountry().getName())
                .withUUIDValue(UUID.fromString(event.getUser().getId()))
                .withStringValue(userLevel)
                .execute();
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
