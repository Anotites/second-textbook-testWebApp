package com.anotites.service;

import com.anotites.pojos.oneToOneExample.EmployeeDetail;
import com.anotites.pojos.oneToOneExample.EmployeeForTableForOneToOne;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class OneToOneService {
    public void saveEmployee(EmployeeForTableForOneToOne employee) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        if (employee.getId() == null) {
            em.persist(employee);
        } else {
            em.merge(employee);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteEmployee(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        EmployeeForTableForOneToOne personForDelete = em.find(EmployeeForTableForOneToOne.class, id);
        if (personForDelete != null) {
            em.getTransaction().begin();
            em.remove(personForDelete);
            em.getTransaction().commit();
        } else {
            System.out.println("Person with id " + id + " not found");
        }
        em.close();
    }

    public EmployeeForTableForOneToOne loadEmployee(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        EmployeeForTableForOneToOne personForLoad = em.find(EmployeeForTableForOneToOne.class, id);
        em.getTransaction().commit();
        em.close();
        return personForLoad;
    }

    public void saveEmployeeDetail(EmployeeDetail employeeDetail) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        if (employeeDetail.getEmployeeId() == null) {
            em.persist(employeeDetail);
        } else {
            em.merge(employeeDetail);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteEmployeeDetail(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            EmployeeDetail detail = em.find(EmployeeDetail.class, id);
            if (detail != null) {
                // Обязательно разрываем связь со стороны сотрудника
                EmployeeForTableForOneToOne employee = detail.getEmployeeForTableForOneToOne();
                if (employee != null) {
                    employee.setEmployeeDetail(null);
                }
                em.remove(detail);
            }

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e; // или хотя бы e.printStackTrace()
        } finally {
            em.close();
        }
    }

    public EmployeeDetail loadEmployeeDetail(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        EmployeeDetail detailForLoad = em.find(EmployeeDetail.class, id);
        em.getTransaction().commit();
        em.close();
        return detailForLoad;
    }
}
