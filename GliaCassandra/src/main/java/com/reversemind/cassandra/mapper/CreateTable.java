package com.reversemind.cassandra.mapper;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.LongSerializer;
import com.netflix.astyanax.serializers.StringSerializer;

/**
 *
 */
public class CreateTable {

    public static void main(String... args) throws ConnectionException {

        Minder minder = new Minder();

        Keyspace keyspace = minder.getKeySpace(Minder.TWITTER_KEY_SPACE);

//        EntityManager<TTUser, Long> entityPersister = new DefaultEntityManager.Builder<TTUser, Long>()
//                .withEntityType(TTUser.class)
//                .withKeyspace(keyspace)
//                .build();
//
//        entityPersister.createStorage(null);


        EntityManager<TUserHistory, Long> entityPersister = new DefaultEntityManager.Builder<TUserHistory, Long>()
                .withEntityType(TUserHistory.class)
                .withKeyspace(keyspace)
                .build();

        entityPersister.createStorage(null);


//        ColumnFamily<Long, String> CQL3_CF = ColumnFamily.newColumnFamily(
//                "Cql3CF",
//                LongSerializer.get(),
//                StringSerializer.get());


//        UPDATE COLUMN FAMILY tuser
//        WITH comparator = UTF8Type
//        AND column_metadata = [{column_name: name, validation_class: UTF8Type, index_type: KEYS}];
//
//
//        UPDATE COLUMN FAMILY tuser
//        WITH comparator = UTF8Type
//        AND column_metadata = [{column_name: screenname, validation_class: UTF8Type, index_type: KEYS}];
//
//        keyspace.createColumnFamily(CQL3_CF, ImmutableMap.<String, Object>builder()
//                .put("column_metadata", ImmutableMap.<String, Object>builder()
//                        .put("Index1", ImmutableMap.<String, Object>builder()
//                                .put("validation_class", "UTF8Type")
//                                .put("index_name",       "Index1")
//                                .put("index_type",       "KEYS")
//                                .build())
//                        .put("Index2", ImmutableMap.<String, Object>builder()
//                                .put("validation_class", "UTF8Type")
//                                .put("index_name",       "Index2")
//                                .put("index_type",       "KEYS")
//                                .build())
//                        .build())
//                .build());

    }

}
