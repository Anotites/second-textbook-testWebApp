package com.anotites.HQL_JPQL_CriteriaQuery;

import com.anotites.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.Optional;

public class TestPersonDao {
    public static void main(String[] args) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        PersonForHQL_JPQLDAO dao = new PersonForHQL_JPQLDAO(em);
        PersonForHQL_JPQLDAO dao2 = new PersonForHQL_JPQLDAO(em, cb);

        try {
            // Начинаем транзакцию для вставки тестовых данных
            em.getTransaction().begin();

            // Создаем несколько записей
            PersonForHQL_JPQL p1 = new PersonForHQL_JPQL(null, "Иван", "Иванов", 29, "Москва");
            PersonForHQL_JPQL p2 = new PersonForHQL_JPQL(null, "Петр", "Петров", 28, "Санкт-Петербург");
            PersonForHQL_JPQL p3 = new PersonForHQL_JPQL(null, "Анна", "Сидорова", 28, "Москва");
            PersonForHQL_JPQL p4 = new PersonForHQL_JPQL(null, "Мария", "Кузнецова", 22, "Казань");
            PersonForHQL_JPQL p5 = new PersonForHQL_JPQL(null, "Иван", "Смирнов", 25, "Казань");

            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);

            em.getTransaction().commit();

            System.out.println("=== Поиск по ID через CriteriaQuery===");
            Optional<PersonForHQL_JPQL> foundCriteriaQuery = dao2.findByIdCriteria(1);
            foundCriteriaQuery.ifPresent(System.out::println);

            System.out.println("=== Пагинация: первая страница (по 2 записи) ===");
            List<PersonForHQL_JPQL> page1 = dao.findByCityWithPagination("Москва", 0, 3);
            page1.forEach(System.out::println);

            System.out.println("\n=== Пагинация: вторая страница (по 2 записи) ===");
            List<PersonForHQL_JPQL> page2 = dao.findByCityWithPagination("Москва", 1, 3);
            page2.forEach(System.out::println);

            System.out.println("=== Поиск человека с максимальным возрастом ===");
            Optional<PersonForHQL_JPQL> foundMax = dao.findOldestPerson();
            foundMax.ifPresent(System.out::println);

            System.out.println("=== Поиск среднего возраста ===");
            Optional<Double> foundAvg = dao.findAverageAge();
            foundAvg.ifPresent(System.out::println);

            System.out.println("=== Поиск суммы возрастов ===");
            Optional<Long> foundSum = dao.findSum();
            foundSum.ifPresent(System.out::println);

            System.out.println("=== Сколько всего людей ===");
            Optional<Long> foundCount = dao.findCount();
            foundCount.ifPresent(System.out::println);

            System.out.println("=== Поиск по ID ===");
            Optional<PersonForHQL_JPQL> found = dao.findById(1);
            found.ifPresent(System.out::println);

            System.out.println("\n=== Поиск по имени (name) ===");
            List<PersonForHQL_JPQL> byName = dao.findByName("Иван");
            byName.forEach(System.out::println);

            System.out.println("\n=== Поиск по имени (name) через CriteriaQuery===");
            List<PersonForHQL_JPQL> byNameCriteriaQuery = dao2.findByNameCriteria("Иван");
            byNameCriteriaQuery.forEach(System.out::println);

            System.out.println("\n=== Поиск по части фразы===");
            List<PersonForHQL_JPQL> c1 = dao2.findWithLike("ова");
            c1.forEach(System.out::println);

            System.out.println("\n=== Поиск по возрасту===");
            List<PersonForHQL_JPQL> c2 = dao2.findWithBetween(20, 50);
            c2.forEach(System.out::println);

            System.out.println("\n=== Поиск по заполненному городу===");
            List<PersonForHQL_JPQL> c3 = dao2.findWithoutNull();
            c3.forEach(System.out::println);

            System.out.println("\n=== Сортировка ===");
            List<PersonForHQL_JPQL> c4 = dao2.orderAGE();
            c4.forEach(System.out::println);

            System.out.println("\n=== Поиск по нескольким полям ===");
            // Ищем людей из Москвы возрастом 25 лет
            List<PersonForHQL_JPQL> searchResult = dao.search(null, "Москва", 25);
            searchResult.forEach(System.out::println);

            // Ищем всех людей по имени Иван (без города и возраста)
            List<PersonForHQL_JPQL> searchResult2 = dao.search("Иван", null, null);
            searchResult2.forEach(System.out::println);

            System.out.println("\n=== Поиск по нескольким полям через CriteriaQuery===");
            // Ищем людей из Москвы возрастом 25 лет
            List<PersonForHQL_JPQL> searchResultCriteriaQuery = dao2.searchCriteria(null, "Москва", 25);
            searchResultCriteriaQuery.forEach(System.out::println);

            System.out.println("\n=== Группировка по городу (количество людей) ===");
            List<CityCount> cityCounts = dao.countByCity();
            cityCounts.forEach(cc ->
                    System.out.println("Город: " + cc.getCity() + ", количество: " + cc.getCount()));

            System.out.println("\n=== Получение DTO (проекция) ===");
            List<PersonView> views = dao.getPersonViews();
            views.forEach(v ->
                    System.out.println("Имя: " + v.getName() + ", фамилия: " + v.getSurname() + ", возраст: " + v.getAge()));

            System.out.println("\n=== Пегинация ===");
            List<PersonForHQL_JPQL> c5 = dao2.orderAGEWithPagination(5, 3);
            c5.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }
}
