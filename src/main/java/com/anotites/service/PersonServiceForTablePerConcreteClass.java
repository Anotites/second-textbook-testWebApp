package com.anotites.service;

import com.anotites.pojos.task8_3.EmployeeForTablePerConcreteClass;
import com.anotites.pojos.task8_3.PersonForTablePerConcreteClass;
import com.anotites.pojos.task8_3.StudentForTablePerConcreteClass;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class PersonServiceForTablePerConcreteClass {

    public PersonForTablePerConcreteClass savePerson(Integer id, Integer age, String name, String surname) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        PersonForTablePerConcreteClass newPerson = new PersonForTablePerConcreteClass(id, age, name, surname);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public StudentForTablePerConcreteClass saveStudent(Integer id, Integer age, String name, String surname, String faculty, Double mark) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        StudentForTablePerConcreteClass newPerson = new StudentForTablePerConcreteClass(id, age, name, surname, faculty, mark);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public EmployeeForTablePerConcreteClass saveEmployee(Integer id, Integer age, String name, String surname, String company, Double salary) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        EmployeeForTablePerConcreteClass newPerson = new EmployeeForTablePerConcreteClass(id, age, name, surname, company, salary);
        em.persist(newPerson);
        em.getTransaction().commit();
        em.close();
        return newPerson;
    }

    public void savePerson(PersonForTablePerConcreteClass person) {
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

    public void saveEmployee(EmployeeForTablePerConcreteClass person) {
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

    public void saveStudent(StudentForTablePerConcreteClass person) {
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
        PersonForTablePerConcreteClass personForDelete = em.find(PersonForTablePerConcreteClass.class, id);
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
    public void deletePerson(PersonForTablePerConcreteClass person) {
        if (person == null || person.getId() == null) {
            System.out.println("Person is null or has no id, cannot delete");
            return;
        }
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();

        try {
            // Если объект не находится в persistent context, merge вернёт управляемую копию
            PersonForTablePerConcreteClass managed = em.contains(person) ? person : em.merge(person);
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

    public PersonForTablePerConcreteClass loadPerson(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        PersonForTablePerConcreteClass personForLoad = em.find(PersonForTablePerConcreteClass.class, id);
        em.close();
        return personForLoad;
    }
}

