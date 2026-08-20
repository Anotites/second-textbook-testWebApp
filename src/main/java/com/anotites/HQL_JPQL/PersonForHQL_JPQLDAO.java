package com.anotites.HQL_JPQL;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersonForHQL_JPQLDAO {

    EntityManager em;
    public PersonForHQL_JPQLDAO( EntityManager em) {
        this.em = em;
    }

    // Поиск по ID
    public Optional<PersonForHQL_JPQL> findById(Integer id) {
        return Optional.ofNullable(em.find(PersonForHQL_JPQL.class, id));
    }

    // Поиск по имени
    public List<PersonForHQL_JPQL> findByName(String name) {
        TypedQuery<PersonForHQL_JPQL> query = em.createQuery(
                "SELECT p FROM PersonForHQL_JPQL p WHERE p.name = :fn", PersonForHQL_JPQL.class);
        query.setParameter("fn", name);
        return query.getResultList();
    }

    // Поиск по нескольким полям (динамический)
    public List<PersonForHQL_JPQL> search(String name, String city, Integer age) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM PersonForHQL_JPQL p WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (name != null && !name.isEmpty()) {
            jpql.append(" AND p.name = :fn");
            params.put("fn", name);
        }
        if (city != null && !city.isEmpty()) {
            jpql.append(" AND p.city = :city");
            params.put("city", city);
        }
        if (age != null) {
            jpql.append(" AND p.age = :age");
            params.put("age", age);
        }

        TypedQuery<PersonForHQL_JPQL> query = em.createQuery(jpql.toString(), PersonForHQL_JPQL.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    // Группировка с DTO
    public List<CityCount> countByCity() {
        TypedQuery<CityCount> query = em.createQuery(
                "SELECT new com.anotites.HQL_JPQL.CityCount(p.city, COUNT(p)) " +
                        "FROM PersonForHQL_JPQL p GROUP BY p.city", CityCount.class);
        return query.getResultList();
    }

    // Возврат DTO проекции
    public List<PersonView> getPersonViews() {
        TypedQuery<PersonView> query = em.createQuery(
                "SELECT new com.anotites.HQL_JPQL.PersonView(p.name, p.surname, p.age) " +
                        "FROM PersonForHQL_JPQL p", PersonView.class);
        return query.getResultList();
    }

    public Optional<PersonForHQL_JPQL> findOldestPerson() {
        String jpql = "SELECT p FROM PersonForHQL_JPQL p " +
                "WHERE p.age = (SELECT MAX(p2.age) FROM PersonForHQL_JPQL p2)";
        TypedQuery<PersonForHQL_JPQL> query = em.createQuery(jpql, PersonForHQL_JPQL.class);
        List<PersonForHQL_JPQL> resultList = query.getResultList();
        return resultList.stream().findFirst(); // если несколько с одинаковым возрастом, берём первого
    }

    public Optional<Double> findAverageAge() {
        String jpql = "SELECT AVG(p.age) FROM PersonForHQL_JPQL p";
        TypedQuery<Double> query = em.createQuery(jpql, Double.class);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Long> findSum() {
        String jpql = "SELECT SUM(p.age) FROM PersonForHQL_JPQL p";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Long> findCount() {
        String jpql = "SELECT COUNT(p.age) FROM PersonForHQL_JPQL p";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<PersonForHQL_JPQL> findByCityWithPagination( String city, int page, int size) {
        String jpql = "SELECT p FROM PersonForHQL_JPQL p WHERE p.city = :city ORDER BY p.id";
        TypedQuery<PersonForHQL_JPQL> query = em.createQuery(jpql, PersonForHQL_JPQL.class);
        query.setParameter("city", city);
        query.setFirstResult(page * size);   // смещение
        query.setMaxResults(size);           // количество записей
        return query.getResultList();
    }
}
