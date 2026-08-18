package com.anotites.service;

import com.anotites.pojos.task8_1.EmployeeForTablePerClass;
import com.anotites.pojos.task8_1.PersonForTablePerClass;
import com.anotites.pojos.task8_1.StudentForTablePerClass;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class PersonServiceForTablePerClass {

    public PersonForTablePerClass savePerson(Integer id, Integer age, String name, String surname) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        PersonForTablePerClass newPerson = new PersonForTablePerClass(id, age, name, surname);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public StudentForTablePerClass saveStudent(Integer id, Integer age, String name, String surname, String faculty, Double mark) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        StudentForTablePerClass newPerson = new StudentForTablePerClass(id, age, name, surname, faculty, mark);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public EmployeeForTablePerClass saveEmployee(Integer id, Integer age, String name, String surname, String company, Double salary) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        EmployeeForTablePerClass newPerson = new EmployeeForTablePerClass(id, age, name, surname, company, salary);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public void savePerson(PersonForTablePerClass person) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        if (person.getId() == null) {
            em.persist(person);
        } else {
            em.merge(person);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deletePerson(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        PersonForTablePerClass personForDelete = em.find(PersonForTablePerClass.class, id);
        if (personForDelete != null) {
            em.getTransaction().begin();
            em.remove(personForDelete);
            em.getTransaction().commit();
        } else {
            System.out.println("Person with id " + id + " not found");
        }
        em.close();
    }

    /**
     * Удаление объекта Person по самому объекту (если он detached, будет выполнен merge).
     */
    public void deletePerson(PersonForTablePerClass person) {
        if (person == null || person.getId() == null) {
            System.out.println("Person is null or has no id, cannot delete");
            return;
        }
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();

        try {
            // Если объект не находится в persistent context, merge вернёт управляемую копию
            PersonForTablePerClass managed = em.contains(person) ? person : em.merge(person);
            em.remove(managed);
            em.getTransaction().commit();
            System.out.println("Deleted person: " + person);
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error deleting person: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public PersonForTablePerClass loadPerson(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        PersonForTablePerClass personForLoad = em.find(PersonForTablePerClass.class, id);
        em.getTransaction().commit();
        em.close();
        return personForLoad;
    }
}

