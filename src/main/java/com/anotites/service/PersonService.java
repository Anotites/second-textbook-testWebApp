package com.anotites.service;

import com.anotites.pojos.Person;
import com.anotites.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;

public class PersonService {

    public Person savePerson(Integer id, Integer age, String name, String surname) {
        Session session = HibernateSessionFactoryUtil.getSession();
        Person newPerson = new Person(id, age, name, surname);
        session.beginTransaction();
        session.persist(newPerson);
        session.getTransaction().commit();
        session.close();
        return newPerson;
    }

    public void deletePerson(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();
        Person personForDelete = session.get(Person.class, id);
        if (personForDelete != null) {
            session.remove(personForDelete);
            session.getTransaction().commit();
        } else {
            System.out.println("Person with id " + id + " not found");
        }
        session.close();
    }

    public Person loadPerson(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();
        Person personForLoad = session.get(Person.class, id);
        session.getTransaction().commit();
        session.close();
        return personForLoad;
    }
}
