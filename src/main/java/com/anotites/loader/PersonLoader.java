package com.anotites.loader;

import com.anotites.pojos.*;
import com.anotites.service.PersonService;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class PersonLoader {
    public static void main(String[] args) {

        Day day = new Day(null, "Sun", "Sunday");
        EntityManager em1 = HibernateUtil.getEntityManager();
        em1.getTransaction().begin();
        em1.persist(day);
        System.out.println("ID ДНЯ " + day.getId());
        em1.getTransaction().commit();

        House house = new House(null, "Max", new Address("1", "Lenina", "Minsk"));
        EntityManager em2 = HibernateUtil.getEntityManager();
        em2.getTransaction().begin();
        em2.persist(house);
        System.out.println("ID ДОМА " + house.getId());
        em2.getTransaction().commit();
        HibernateUtil.close();

//        Animal animal = new Animal(null, 6, "Max", "Dog");
//        EntityManager em = HibernateUtil.getEntityManager();
//        em.getTransaction().begin();
//        em.persist(animal);
//        em.getTransaction().commit();
//        HibernateUtil.close();

//        Person person = new Person(null, 35, "Max", "Petrov");
//        EntityManager em = HibernateUtil.getEntityManager();
//        em.getTransaction().begin();
//        em.persist(person);
//        em.getTransaction().commit();
//        HibernateUtil.close();

//        PersonService personService = new PersonService();
//        Person person1 = personService.loadPerson(72);
//        System.out.println("Имя " + person1.getName());
//
//        personService.savePerson(null, 33, "Oleg", "Smirnov");
//        personService.savePerson(null, 30, "Oleg", "Smirnov");

//        personService.deletePerson(11);
//        personService.demonstrateDifference();
//        personService.demonstrateFlushAndClear();
//        personService.demonstrateRefresh();
//        personService.demonstrateTriggerSync();
//        personService.deletePerson(person1);
//        personService.createAndDeletePerson(30, "Oleg", "SmirnovForDelete");
//        Person PersonForDelete = personService.savePerson(null, 30, "Oleg", "SmirnovForDelete2");
//        personService.createAndDeletePerson(PersonForDelete);
//        personService.demonstrateCreateAndDeleteInSingleTransaction();
    }
}
