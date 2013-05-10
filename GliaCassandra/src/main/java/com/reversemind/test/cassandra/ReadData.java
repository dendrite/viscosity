package com.reversemind.test.cassandra;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.model.Column;

import java.io.Serializable;

/**
 * Date: 5/7/13
 * Time: 3:07 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ReadData implements Serializable {

    public static void main(String... args) throws Exception {
        Keyspace keyspace = CreateKeySpaceAndColumnFamily.getKeyspace();

        // Query a single column
        Column<String> result = keyspace.prepareQuery(CreateKeySpaceAndColumnFamily.CF_STATUS)
                .getKey("key003")
                .getColumn("Column1")
                .execute().getResult();
        String value = result.getStringValue();
        System.out.println(value);

        result = keyspace.prepareQuery(CreateKeySpaceAndColumnFamily.CF_STATUS)
                .getKey("key003")
                .getColumn("Column4")
                .execute().getResult();
        System.out.println(result.getStringValue());

        result = keyspace.prepareQuery(CreateKeySpaceAndColumnFamily.CF_STATUS)
                .getKey("key004")
                .getColumn("Column9")
                .execute().getResult();
        System.out.println(result.getStringValue());

    }

}
