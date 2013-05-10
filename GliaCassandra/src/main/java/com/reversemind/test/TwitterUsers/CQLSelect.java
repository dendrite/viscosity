package com.reversemind.test.TwitterUsers;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

/**
 * Created with IntelliJ IDEA.
 * User: dendrite
 * Date: 10.05.13
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public class CQLSelect {

    public static void main(String... args) throws ConnectionException {

        // CQL
        // CREATE KEYSPACE Excelsior WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

        Keyspace keyspace = Minder.getKeySpace("TWITTER");

        ColumnFamily<Integer, String> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                IntegerSerializer.get(),
                StringSerializer.get());


        OperationResult<CqlResult<Integer, String>> result
                = keyspace.prepareQuery(CQL3_CF)
                .withCql("SELECT * FROM TUser where screenName='screenName_5004';")
                .execute();

        //System.out.println("CQL Count: " + result.getResult().getNumber());
        System.out.println(result);
        System.out.println(result.getResult().getRows());

        for (Row<Integer, String> row : result.getResult().getRows()) {
            System.out.println("CQL Key: " + row.getKey());

            ColumnList<String> columns = row.getColumns();
//            for(com.netflix.astyanax.model.Column<String> column: columns){
//                System.out.println(column.getName() + "|" + column.getStringValue());
//            }
            System.out.println("   id      : " + columns.getLongValue("id", null));
            System.out.println("   name : " + columns.getStringValue("name", null));
            System.out.println("   screenName  : " + columns.getStringValue("screenname", null));
        }

    }

}
