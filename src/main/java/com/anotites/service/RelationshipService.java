package com.anotites.service;

import com.anotites.pojos.Relationship.Department;
import com.anotites.pojos.Relationship.EmployeeDetail;
import com.anotites.pojos.Relationship.EmployeeForRelationship;
import com.anotites.pojos.Relationship.Meeting;
import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.Set;

public class RelationshipService {
    public void saveEmployee(EmployeeForRelationship employee) {
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
        EmployeeForRelationship personForDelete = em.find(EmployeeForRelationship.class, id);
        if (personForDelete != null) {
            em.getTransaction().begin();
            em.remove(personForDelete);
            em.getTransaction().commit();
        } else {
            System.out.println("Person with id " + id + " not found");
        }
        em.close();
    }

    public EmployeeForRelationship loadEmployee(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        EmployeeForRelationship personForLoad = em.find(EmployeeForRelationship.class, id);
        em.getTransaction().commit();
        em.close();
        return personForLoad;
    }

    public void saveEmployeeDetail(EmployeeDetail employeeDetail) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(employeeDetail);  // merge вместо persist
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteEmployeeDetail(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            EmployeeDetail detail = em.find(EmployeeDetail.class, id);
            if (detail != null) {
                // Обязательно разрываем связь со стороны сотрудника
                EmployeeForRelationship employee = detail.getEmployeeForRelationship();
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
            throw e;
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

    public void saveDepartment(Department department) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        if (department.getDepartmentId() == null) {
            em.persist(department);
        } else {
            em.merge(department);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteDepartment(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Department department = em.find(Department.class, id);
            if (department != null) {
                // Загружаем сотрудников отдела и разрываем связи
                Set<EmployeeForRelationship> employees = department.getEmployees();
                if (employees != null) {
                    for (EmployeeForRelationship employee : employees) {
                        employee.setDepartment(null);
                    }
                }
                em.remove(department);
            } else {
                System.out.println("Department with id " + id + " not found");
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Department loadDepartment(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Department departmentForLoad = em.find(Department.class, id);
        em.getTransaction().commit();
        em.close();
        return departmentForLoad;
    }

    public void saveMeeting(Meeting meeting) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        if (meeting.getMeetingId() == null) {
            em.persist(meeting);
        } else {
            em.merge(meeting);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteMeeting(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Meeting meeting = em.find(Meeting.class, id);
            if (meeting != null) {
                // Загружаем сотрудников и разрываем связи
                Set<EmployeeForRelationship> employees = meeting.getEmployees();
                if (employees != null) {
                    for (EmployeeForRelationship employee : employees) {
                        employee.getMeetings().remove(meeting);
                    }
                }
                em.remove(meeting);
            } else {
                System.out.println("Meeting with id " + id + " not found");
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Meeting loadMeeting(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Meeting meeting = em.find(Meeting.class, id);
        em.getTransaction().commit();
        em.close();
        return meeting;
    }

    public void assignEmployeeToMeeting(Integer employeeId, Integer meetingId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            EmployeeForRelationship employee = em.find(EmployeeForRelationship.class, employeeId);
            Meeting meeting = em.find(Meeting.class, meetingId);

            if (employee == null || meeting == null) {
                System.out.println("Сотрудник или встреча не найдены.");
                em.getTransaction().commit();  // просто завершаем транзакцию, ничего не меняли
                return;
            }

            meeting.getEmployees().add(employee);   // изменяем сторону-владельца
            employee.getMeetings().add(meeting);    // поддерживаем двустороннюю связь в памяти

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
