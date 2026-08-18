package com.anotites.loader;

import com.anotites.pojos.oneToOneExample.EmployeeDetail;
import com.anotites.pojos.oneToOneExample.EmployeeForTableForOneToOne;
import com.anotites.service.OneToOneService;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class OneToOneExampleLoader {
    public static void main(String[] args) {
        EmployeeForTableForOneToOne employee = new EmployeeForTableForOneToOne(
                null,    // id – будет сгенерирован
                37,
                "Oleg",
                "Ivanov",
                null
        );
        EmployeeDetail employeeDetail = new EmployeeDetail(
                null,    // id – будет сгенерирован
                "37",
                "Lenina",
                "Minsk",
                null
        );
        employee.setEmployeeDetail(employeeDetail);
        employeeDetail.setEmployeeForTableForOneToOne(employee);
        EntityManager em1 = HibernateUtil.getEntityManager();
        em1.getTransaction().begin();
        em1.persist(employee);
        em1.getTransaction().commit();
        em1.close();
        HibernateUtil.close();

//        OneToOneService oneToOneService = new OneToOneService();
//        oneToOneService.deleteEmployee(2);
//        oneToOneService.saveEmployee(employee);
//        EmployeeForTableForOneToOne p = oneToOneService.loadEmployee(3);
//        System.out.println("Возраст " + p.getAge());
//        oneToOneService.deleteEmployeeDetail(3);
//        EmployeeDetail e = oneToOneService.loadEmployeeDetail(3);
//        System.out.println("Город " + e.getCity());
    }
}