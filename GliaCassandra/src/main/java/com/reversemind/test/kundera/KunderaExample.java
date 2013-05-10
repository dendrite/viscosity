package com.reversemind.test.kundera;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class KunderaExample {
    public static void main(String[] args) {

        // 1 - in cqlsh
        // CREATE KEYSPACE "KunderaExamples" WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

        // 2 - cassandra-cli
        // use KunderaExamples;
        // create column family users with comparator=UTF8Type and default_validation_class=UTF8Type and key_validation_class=UTF8Type;



        // this. stuff is WORKING
        // in cassandra-cli
        //create column family users with comparator=UTF8Type and default_validation_class=UTF8Type and key_validation_class=UTF8Type;

        // this stuff is not working
        //CREATE TABLE users (user_id varchar, first_name varchar, last_name varchar, city varchar, screenName varchar, PRIMARY KEY (user_id, screenName));


        //        Map propertyMap = new HashMap();
//        //propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_2_0);
//        propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu", propertyMap);
////        //em.setProperty("cql.version", "3.0.0");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
        EntityManager em = emf.createEntityManager();



        long epoch = 10;
        for(int i=0; i<10; i++){

            User user = new User();
            user.setUserId("000" + i);
            user.setFirstName("John_" + i);
            user.setLastName("Smith_" + i);
            user.setCity("London_" + i);
            user.setScreenName("GOGO");
            if(i == 0){
                user.setEpoch(epoch++);
                em.persist(user);

                user.setEpoch(epoch++);
                em.persist(user);
            }
            user.setEpoch(epoch++);
            em.persist(user);
        }



        User us = em.find(User.class, "0005");
        System.out.println("us:" + us);

        em.close();
        emf.close();

    }
}