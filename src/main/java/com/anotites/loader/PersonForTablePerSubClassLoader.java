package com.anotites.loader;

import com.anotites.pojos.task8_2.EmployeeForTablePerSubClass;
import com.anotites.pojos.task8_2.StudentForTablePerSubClass;
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
        HibernateUtil.close();
    }
}
