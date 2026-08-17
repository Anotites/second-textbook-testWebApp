package com.anotites.loader;

import com.anotites.pojos.task8_3.EmployeeForTablePerConcreteClass;
import com.anotites.pojos.task8_3.StudentForTablePerConcreteClass;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class PersonForTablePerConcreteClassLoader {
    public static void main(String[] args) {
        StudentForTablePerConcreteClass student = new StudentForTablePerConcreteClass(
                null,    // id – будет сгенерирован
                35,
                "Max",
                "Smirnov",
                "Computer Science",
                4.8
        );
        EmployeeForTablePerConcreteClass employee = new EmployeeForTablePerConcreteClass(
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
