package com.anotites.loader;

import com.anotites.pojos.task8_2.EmployeeForTablePerSubClass;
import com.anotites.pojos.task8_2.PersonForTablePerSubClass;
import com.anotites.pojos.task8_2.StudentForTablePerSubClass;
import com.anotites.service.PersonServiceForTablePerSubClass;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class PersonForTablePerSubClassLoader {
    public static void main(String[] args) {
        StudentForTablePerSubClass student = new StudentForTablePerSubClass(
                null,    // id – будет сгенерирован
                35,
                "Max",
                "Smirnov",
                "Computer Science",
                4.8
        );
        EmployeeForTablePerSubClass employee = new EmployeeForTablePerSubClass(
                null,    // id – будет сгенерирован
                35,
                "Oleg",
                "Smirnov",
                "Epam",
                4400D
        );
        EntityManager em1 = HibernateUtil.getEntityManager();
        EntityManager em2 = HibernateUtil.getEntityManager();
        em1.getTransaction().begin();
        em1.persist(student);
        em1.getTransaction().commit();
        em2.getTransaction().begin();
        em2.persist(employee);
        em2.getTransaction().commit();
        em2.detach(employee);

        PersonServiceForTablePerSubClass personService = new PersonServiceForTablePerSubClass();
        personService.deletePerson(1);
        personService.saveEmployee(employee);
        personService.deletePerson(employee);
        PersonForTablePerSubClass p = personService.loadPerson(2);
        if (p != null) {
            System.out.println("Возраст " + p.getAge());
        } else {
            System.out.println("Person not found");
        }
        em2.close();
        em1.close();
        HibernateUtil.close();
    }
}
