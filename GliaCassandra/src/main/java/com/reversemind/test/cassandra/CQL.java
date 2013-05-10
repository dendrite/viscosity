package com.reversemind.test.cassandra;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;

import java.io.Serializable;

/**
 * Date: 5/7/13
 * Time: 5:04 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class CQL implements Serializable {

    public static void main(String... args) {

        Keyspace keyspace = CreateKeySpaceAndColumnFamily.getKeyspace();
        try {
            OperationResult<CqlResult<String, String>> result
                    = keyspace.prepareQuery(CreateKeySpaceAndColumnFamily.CF_STATUS)
                    .withCql("SELECT * FROM \"" + CreateKeySpaceAndColumnFamily.STATUS + "\";")
                    .execute();
            for (Row<String, String> row : result.getResult().getRows()) {
                //System.out.println(row.getColumns().getColumnNames());

                ColumnList<String> columns = row.getColumns();
                for(com.netflix.astyanax.model.Column column: columns){
                    System.out.println(column.getStringValue());
                }

            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

    }

}
