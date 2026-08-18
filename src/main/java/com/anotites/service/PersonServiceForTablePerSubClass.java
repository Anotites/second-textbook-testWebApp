package com.anotites.service;

import com.anotites.pojos.task8_2.EmployeeForTablePerSubClass;
import com.anotites.pojos.task8_2.PersonForTablePerSubClass;
import com.anotites.pojos.task8_2.StudentForTablePerSubClass;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class PersonServiceForTablePerSubClass {

    public PersonForTablePerSubClass savePerson(Integer id, Integer age, String name, String surname) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        PersonForTablePerSubClass newPerson = new PersonForTablePerSubClass(id, age, name, surname);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public StudentForTablePerSubClass saveStudent(Integer id, Integer age, String name, String surname, String faculty, Double mark) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        StudentForTablePerSubClass newPerson = new StudentForTablePerSubClass(id, age, name, surname, faculty, mark);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public EmployeeForTablePerSubClass saveEmployee(Integer id, Integer age, String name, String surname, String company, Double salary) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        EmployeeForTablePerSubClass newPerson = new EmployeeForTablePerSubClass(id, age, name, surname, company, salary);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public void savePerson(PersonForTablePerSubClass person) {
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

    public void saveEmployee(EmployeeForTablePerSubClass person) {
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

    public void saveStudent(StudentForTablePerSubClass person) {
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
        PersonForTablePerSubClass personForDelete = em.find(PersonForTablePerSubClass.class, id);
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
    public void deletePerson(PersonForTablePerSubClass person) {
        if (person == null || person.getId() == null) {
            System.out.println("Person is null or has no id, cannot delete");
            return;
        }
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();

        try {
            // Если объект не находится в persistent context, merge вернёт управляемую копию
            PersonForTablePerSubClass managed = em.contains(person) ? person : em.merge(person);
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

    public PersonForTablePerSubClass loadPerson(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        PersonForTablePerSubClass personForLoad = em.find(PersonForTablePerSubClass.class, id);
        em.close();
        return personForLoad;
    }
}

