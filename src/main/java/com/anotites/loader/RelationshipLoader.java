package com.anotites.loader;

import com.anotites.pojos.Relationship.Department;
import com.anotites.pojos.Relationship.EmployeeDetail;
import com.anotites.pojos.Relationship.EmployeeForRelationship;
import com.anotites.pojos.Relationship.Meeting;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.Set;

public class RelationshipLoader {
    public static void main(String[] args) {
        EmployeeForRelationship employee = new EmployeeForRelationship(
                null,    // id – будет сгенерирован
                38,
                "Oleg",
                "Ivanov",
                null
        );
        EmployeeDetail employeeDetail = new EmployeeDetail(
                null,    // id – будет сгенерирован
                "38",
                "Lenina",
                "Minsk",
                null
        );
        Set<EmployeeForRelationship> employees = new HashSet<>();
        employees.add(employee);
        Department department = new Department(null, "SalesFor4Region", null);
        Meeting meeting = new Meeting(null,"Monday",null);
        Set<Meeting> meetings = new HashSet<>();
        meetings.add(meeting);
        employee.setEmployeeDetail(employeeDetail);
        employeeDetail.setEmployeeForRelationship(employee);
        employee.setDepartment(department);
        department.setEmployees(employees);
        employee.setMeetings(meetings);
        meeting.setEmployees(employees);
        EntityManager em1 = HibernateUtil.getEntityManager();
        em1.getTransaction().begin();
        em1.persist(department);   // сохраняем отдел, чтобы FK не ругался
        em1.persist(meeting);
        em1.persist(employee);    // каскадно сохранит и EmployeeDetail
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