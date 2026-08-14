package com.anotites.loader;

import com.anotites.pojos.Person;
import com.anotites.service.PersonService;

public class PersonLoader {
    public static void main(String[] args) {
//        Person person = new Person(null, 35, "Max", "Petrov");
//        EntityManager em = HibernateUtil.getEntityManager();
//        em.getTransaction().begin();
//        em.persist(person);
//        em.getTransaction().commit();
//        HibernateUtil.close();

        PersonService personService = new PersonService();
        Person person1 = personService.loadPerson(1);
        System.out.println("Имя " + person1.getName());

        personService.savePerson(null, 33, "Oleg", "Smirnov");
        personService.savePerson(null, 30, "Oleg", "Smirnov");

        personService.deletePerson(11);
    }
}
