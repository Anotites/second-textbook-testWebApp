package com.anotites.HQL_JPQL_CriteriaQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.*;

public class PersonForHQL_JPQLDAO {

    EntityManager em;
    CriteriaBuilder cb;

    public PersonForHQL_JPQLDAO(EntityManager em) {
        this.em = em;
    }

    public PersonForHQL_JPQLDAO(EntityManager em, CriteriaBuilder cb) {
        this.em = em;
        this.cb = cb;
    }

    // Поиск по ID
    public Optional<PersonForHQL_JPQL> findById(Integer id) {
        return Optional.ofNullable(em.find(PersonForHQL_JPQL.class, id));
    }

    public Optional<PersonForHQL_JPQL> findByIdCriteria(Integer id) {
        CriteriaQuery<PersonForHQL_JPQL> criteria = cb.createQuery(PersonForHQL_JPQL.class);
        Root<PersonForHQL_JPQL> person = criteria.from(PersonForHQL_JPQL.class);
        criteria.select(person).where(cb.equal(person.get("id"), id));
        return Optional.ofNullable(em.createQuery(criteria).getResultList().getFirst());
    }

    public List<PersonForHQL_JPQL> findWithLike(String string) {
        CriteriaQuery<PersonForHQL_JPQL> criteria = cb.createQuery(PersonForHQL_JPQL.class);
        Root<PersonForHQL_JPQL> person = criteria.from(PersonForHQL_JPQL.class);
        criteria.select(person).where(cb.like(cb.lower(person.get("surname")), ("%" + string + "%").toLowerCase()));
        return em.createQuery(criteria).getResultList();
    }

    public List<PersonForHQL_JPQL> findWithBetween(int a, int b) {
        CriteriaQuery<PersonForHQL_JPQL> criteria = cb.createQuery(PersonForHQL_JPQL.class);
        Root<PersonForHQL_JPQL> person = criteria.from(PersonForHQL_JPQL.class);
        criteria.select(person).where(cb.between(person.get("age"), a, b));
        return em.createQuery(criteria).getResultList();
    }

    public List<PersonForHQL_JPQL> findWithoutNull() {
        CriteriaQuery<PersonForHQL_JPQL> criteria = cb.createQuery(PersonForHQL_JPQL.class);
        Root<PersonForHQL_JPQL> person = criteria.from(PersonForHQL_JPQL.class);
        criteria.select(person).where(cb.isNotNull(person.get("city")));
        return em.createQuery(criteria).getResultList();
    }

    public List<PersonForHQL_JPQL> orderAGE() {
        CriteriaQuery<PersonForHQL_JPQL> criteria = cb.createQuery(PersonForHQL_JPQL.class);
        Root<PersonForHQL_JPQL> person = criteria.from(PersonForHQL_JPQL.class);
        criteria.select(person).orderBy(cb.desc(person.get("age")), cb.asc(person.get("surname")));
        return em.createQuery(criteria).getResultList();
    }

    // Поиск по имени
    public List<PersonForHQL_JPQL> findByName(String name) {
        TypedQuery<PersonForHQL_JPQL> query = em.createQuery(
                "SELECT p FROM PersonForHQL_JPQL p WHERE p.name = :fn", PersonForHQL_JPQL.class);
        query.setParameter("fn", name);
        return query.getResultList();
    }

    public List<PersonForHQL_JPQL> findByNameCriteria(String name) {
        CriteriaQuery<PersonForHQL_JPQL> criteria = cb.createQuery(PersonForHQL_JPQL.class);
        Root<PersonForHQL_JPQL> person = criteria.from(PersonForHQL_JPQL.class);
        criteria.select(person).where(cb.equal(person.get("name"), name));
        return em.createQuery(criteria).getResultList();
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

    public List<PersonForHQL_JPQL> searchCriteria(String name, String city, Integer age) {
        CriteriaQuery<PersonForHQL_JPQL> criteria = cb.createQuery(PersonForHQL_JPQL.class);
        Root<PersonForHQL_JPQL> person = criteria.from(PersonForHQL_JPQL.class);

        criteria.select(person);

        List<Predicate> predicates = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.equal(person.get("name"), name));
        }
        if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(person.get("city"), city));
        }
        if (age != null) {
            predicates.add(cb.equal(person.get("age"), age));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(criteria).getResultList();
    }

    // Группировка с DTO
    public List<CityCount> countByCity() {
        TypedQuery<CityCount> query = em.createQuery(
                "SELECT new com.anotites.HQL_JPQL_CriteriaQuery.CityCount(p.city, COUNT(p)) " +
                        "FROM PersonForHQL_JPQL p GROUP BY p.city", CityCount.class);
        return query.getResultList();
    }

    // Возврат DTO проекции
    public List<PersonView> getPersonViews() {
        TypedQuery<PersonView> query = em.createQuery(
                "SELECT new com.anotites.HQL_JPQL_CriteriaQuery.PersonView(p.name, p.surname, p.age) " +
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

    public List<PersonForHQL_JPQL> findByCityWithPagination(String city, int page, int size) {
        String jpql = "SELECT p FROM PersonForHQL_JPQL p WHERE p.city = :city ORDER BY p.id";
        TypedQuery<PersonForHQL_JPQL> query = em.createQuery(jpql, PersonForHQL_JPQL.class);
        query.setParameter("city", city);
        query.setFirstResult(page * size);   // смещение
        query.setMaxResults(size);           // количество записей
        return query.getResultList();
    }

    public List<PersonForHQL_JPQL> orderAGEWithPagination(int page, int size) {
        CriteriaQuery<PersonForHQL_JPQL> criteria = cb.createQuery(PersonForHQL_JPQL.class);
        Root<PersonForHQL_JPQL> person = criteria.from(PersonForHQL_JPQL.class);
        criteria.select(person).orderBy(cb.desc(person.get("age")), cb.asc(person.get("surname")));
        TypedQuery<PersonForHQL_JPQL> t = em.createQuery(criteria);
        t.setFirstResult(size * (page - 1));
        t.setMaxResults(3);
        return t.getResultList();
    }
}
