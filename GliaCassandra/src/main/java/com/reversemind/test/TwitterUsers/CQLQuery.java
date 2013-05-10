package com.reversemind.test.TwitterUsers;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.serializers.LongSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

/**
 *
 */
public class CQLQuery {

    public static void main(String... args) throws ConnectionException {

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);


        ColumnFamily<Long, String> CQL3_CF = ColumnFamily.newColumnFamily(
                "Cql3CF",
                LongSerializer.get(),
                StringSerializer.get());

        OperationResult<CqlResult<Long, String>> result;
        result = keyspace
                .prepareQuery(CQL3_CF)
                .withCql("SELECT * FROM TUser where screenName = 'screenName5004';")
                .execute();

        for (Row<Long, String> row : result.getResult().getRows()) {
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
