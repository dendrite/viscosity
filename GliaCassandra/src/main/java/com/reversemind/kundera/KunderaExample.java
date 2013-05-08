package com.reversemind.kundera;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class KunderaExample {
    public static void main(String[] args) {

        User user = new User();
        user.setUserId("0002");
        user.setFirstName("John2");
        user.setLastName("Smith2");
        user.setCity("London2");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
        EntityManager em = emf.createEntityManager();

        em.persist(user);
        em.close();
        emf.close();

    }
}