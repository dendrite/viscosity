package com.reversemind.test.cassandra;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;

import java.io.Serializable;

/**
 * Date: 5/7/13
 * Time: 2:25 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class WriteData implements Serializable {



    /**
     *
     * @param args
     */
    public static void main(String... args) {

        Keyspace keyspace = CreateKeySpaceAndColumnFamily.getKeyspace();

        MutationBatch m = keyspace.prepareMutationBatch();

        // Setting columns in a standard column
        m.withRow(CreateKeySpaceAndColumnFamily.CF_STATUS, "key001")
                .putColumn("Column1", "11", null)
                .putColumn("Column2", "22", null);

        m.withRow(CreateKeySpaceAndColumnFamily.CF_STATUS, "key002")
                .putColumn("Column1", "33", null);

        m.withRow(CreateKeySpaceAndColumnFamily.CF_STATUS, "key003")
                .putColumn("Column1", "44", null)
                .putColumn("Column2", "55", null)
                .putColumn("Column3", "66", null)
                .putColumn("Column4", "77", null);

        m.withRow(CreateKeySpaceAndColumnFamily.CF_STATUS, "key004")
                .putColumn("Column9", "99", null);

        m.setTimestamp(System.currentTimeMillis());

        try {
            OperationResult<Void> result = m.execute();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

    }

}
