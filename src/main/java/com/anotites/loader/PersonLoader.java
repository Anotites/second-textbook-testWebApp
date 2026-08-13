package com.anotites.loader;

import com.anotites.pojos.Person;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class PersonLoader {
    public static void main(String[] args) {
        Person person = new Person(null, 35, "Max", "Petrov");
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        HibernateUtil.close();
    }
}
