package com.reversemind.storm.jetter.cassandra;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.StringSerializer;


/**
 *
 */
public class CreateTable {

    public static void main(String... args) throws Exception{

        Keyspace keyspace = CassandraSettings.getKeySpace(CassandraSettings.KEY_SPACE);

        ColumnFamily<String, String> CQL3_CF = ColumnFamily.newColumnFamily(
                CassandraSettings.CQL_COLUMN_FAMILY,
                StringSerializer.get(),
                StringSerializer.get());
//
//        OperationResult<CqlResult<Integer, Integer>> result = keyspace
//                .prepareQuery(CQL3_CF)
//                .withCql("CREATE TABLE users (user_id int, screenName int, otherParameters int, PRIMARY KEY (user_id, screenName));")
//                .execute();

        OperationResult<CqlResult<String, String>> result = keyspace
                .prepareQuery(CQL3_CF)
                .withCql("CREATE TABLE events (" +
                        " event_id timeuuid, " +
                        " eventType varchar, " +
                        " country varchar, " +
                        " user_id uuid, " +
                        " userLevel varchar, " +
                        " PRIMARY KEY ((event_id, eventType, country), user_id));")
                .execute();

        // CREATE INDEX idx_user ON users (user_id);

        System.out.println(result.getResult());

    }

}
