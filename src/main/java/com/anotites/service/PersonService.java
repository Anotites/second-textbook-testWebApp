package com.anotites.service;

import com.anotites.pojos.Person;
import com.anotites.util.HibernateSessionFactoryUtil;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class PersonService {

    public Person savePerson(Integer id, Integer age, String name, String surname) {
        Session session = HibernateSessionFactoryUtil.getSession();
        Person newPerson = new Person(id, age, name, surname);
        session.beginTransaction();
        session.persist(newPerson);
        session.getTransaction().commit();
        session.close();
        return newPerson;
    }

    public void deletePerson(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();
        Person personForDelete = session.get(Person.class, id);
        if (personForDelete != null) {
            session.remove(personForDelete);
            session.getTransaction().commit();
        } else {
            System.out.println("Person with id " + id + " not found");
        }
        session.close();
    }

    public Person loadPerson(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();
        Person personForLoad = session.get(Person.class, id);
        session.getTransaction().commit();
        session.close();
        return personForLoad;
    }

    // ----- НОВЫЕ МЕТОДЫ ДЛЯ ЗАДАНИЯ -----

    /**
     * Демонстрация разницы между find() и getReference().
     * Выводит результаты на экран.
     */
    public void demonstrateDifference() {
        System.out.println("=== Демонстрация разницы между find() и getReference() ===\n");

        // 1. Создаём тестовую запись
        System.out.println("1. Создаём тестовую запись...");
        Person saved = savePerson(null, 30, "Demo", "User");
        Integer demoId = saved.getId();
        System.out.println("   Создан Person с id=" + demoId + "\n");

        // 2. find() для существующей записи
        System.out.println("2. find() для существующей записи (id=" + demoId + "):");
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Person found = session.find(Person.class, demoId);
            System.out.println("   Результат: " + found);
            System.out.println("   Класс объекта: " + found.getClass().getName());
            System.out.println("   Имя: " + found.getName() + "\n");
        }

        // 3. getReference() для существующей записи (сессия ОТКРЫТА)
        System.out.println("3. getReference() для существующей записи (id=" + demoId + "):");
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Person referenced = session.getReference(Person.class, demoId);
            System.out.println("   Результат (прокси): " + referenced);
            System.out.println("   Класс объекта: " + referenced.getClass().getName());
            System.out.println("   Имя (после обращения к полю): " + referenced.getName() + "\n");
        }

        // 4. find() для несуществующей записи
        int nonExistingId = 9999;
        System.out.println("4. find() для несуществующей записи (id=" + nonExistingId + "):");
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Person notFound = session.find(Person.class, nonExistingId);
            System.out.println("   Результат: " + notFound + " (null)\n");
        }

        // 5. getReference() для несуществующей записи
        System.out.println("5. getReference() для несуществующей записи (id=" + nonExistingId + "):");
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Person notFoundRef = session.getReference(Person.class, nonExistingId);
            System.out.println("   Прокси создан: " + notFoundRef);
            System.out.println("   Пытаемся обратиться к полю...");
            String name = notFoundRef.getName(); // Здесь вылетит исключение
            System.out.println("   Имя: " + name);
        } catch (EntityNotFoundException e) {
            System.out.println("   ❌ EntityNotFoundException: " + e.getMessage());
        } catch (HibernateException e) {
            System.out.println("   ❌ HibernateException: " + e.getMessage());
        }
        System.out.println();

        // 6. Удаляем тестовую запись
        System.out.println("6. Удаляем тестовую запись (id=" + demoId + ")");
        deletePerson(demoId);
        System.out.println("   Запись удалена.\n");
        System.out.println("=== Демонстрация завершена ===");
    }

    public void demonstrateFlushAndClear() {
        System.out.println("=== flush() и clear() ===\n");
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();

        // 1. Создаём объект и persist
        Person person = new Person(null, 25, "FlushTest", "User");
        session.persist(person);
        System.out.println("Объект создан: " + person);

        // 2. Изменяем имя после persist, но до commit
        person.setName("ChangedName");
        System.out.println("Имя изменено в объекте на: " + person.getName());

        // 3. Вызываем flush() – Hibernate выполнит INSERT и UPDATE до commit
        session.flush();
        System.out.println("Выполнен flush(). SQL отправлен в БД, но транзакция не завершена.");

        // 4. Очищаем persistence context
        session.clear();
        System.out.println("Persistence context очищен. Объект отсоединён.\n");

        // 5. Загружаем ту же запись из БД, чтобы увидеть актуальное состояние
        Person loaded = session.get(Person.class, person.getId());
        System.out.println("Загружено из БД после clear(): " + loaded);
        System.out.println("Имя в БД: " + loaded.getName() + " (должно быть 'ChangedName')\n");
        session.getTransaction().commit();
        session.close();
    }

    public void demonstrateRefresh() {
        System.out.println("=== refresh() ===\n");
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();

        // Создаём тестовую запись
        Person person = new Person(null, 40, "RefreshTest", "User");
        session.persist(person);
        Integer id = person.getId();
        session.flush();
        System.out.println("Создан объект с id=" + id + " и name='RefreshTest'");

        // Изменяем объект в памяти, но НЕ сохраняем
        person.setName("NewName");
        System.out.println("Имя изменено в объекте на 'NewName', но не сохранено.");

        // Вызываем refresh() – данные перечитаются из БД и перезапишут изменения объекта
        session.refresh(person);
        System.out.println("Выполнен refresh(). Объект синхронизирован с БД.");
        System.out.println("Имя после refresh(): " + person.getName() + " (должно быть 'RefreshTest')\n");
        session.getTransaction().commit();
        session.close();
    }

    public void demonstrateTriggerSync() {
        System.out.println("=== Триггеры и refresh() ===\n");
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();

        // Создаём объект с age = 30, но триггер в БД заменит age на 100
        Person person = new Person(null, 30, "TriggerTest", "User");
        session.persist(person);
        System.out.println("Объект создан с age=30 и отправлен в БД.");

        // Важно: после persist объект остался в кеше с age=30, хотя в БД уже 100
        System.out.println("Значение age в объекте ДО refresh(): " + person.getAge());

        // Вызываем refresh() для перечитывания данных из БД
        session.flush();         // <-- обязательно
        session.refresh(person);
        System.out.println("Значение age ПОСЛЕ refresh(): " + person.getAge() + " (должно быть 100)\n");
        session.getTransaction().commit();
        session.close();
    }

    // flush() – явная отправка SQL, может снизить производительность.
    // clear() – отсоединяет все объекты, что может привести к LazyInitializationException, если использовать их позже.
    // refresh() – перезаписывает локальные изменения данными из БД, что может быть неожиданным.
    // Триггеры – Hibernate не знает о них, требуется refresh() для синхронизации, и обязательно после flush().

    /**
     * Удаление объекта Person по самому объекту (если он detached, будет выполнен merge).
     */
    public void deletePerson(Person person) {
        if (person == null || person.getId() == null) {
            System.out.println("Person is null or has no id, cannot delete");
            return;
        }
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();
        try {
            // Если объект не находится в persistent context, merge вернёт управляемую копию
            Person managed = session.contains(person) ? person : session.merge(person);
            session.remove(managed);
            session.getTransaction().commit();
            System.out.println("Deleted person: " + person);
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.err.println("Error deleting person: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Перегруженный метод: сначала создаёт объект в БД, затем сразу удаляет его.
     * Использует отдельные транзакции (savePerson уже открывает и закрывает сессию).
     */
    public void createAndDeletePerson(Integer age, String name, String surname) {
        System.out.println("Создаём и удаляем Person (age=" + age + ", name=" + name + ", surname=" + surname + ")");
        Person saved = savePerson(null, age, name, surname);
        System.out.println("Создан объект с id=" + saved.getId());
        deletePerson(saved.getId());
        System.out.println("Объект удалён. Проверяем...");
        Person check = loadPerson(saved.getId());
        if (check == null) {
            System.out.println("Удаление подтверждено: объект отсутствует в БД.");
        } else {
            System.out.println("ВНИМАНИЕ: объект всё ещё существует!");
        }
    }

    /**
     * Перегруженный метод: принимает готовый объект Person, сохраняет и сразу удаляет.
     */
    public void createAndDeletePerson(Person person) {
        if (person == null) {
            System.out.println("Передан null, операция невозможна");
            return;
        }
        createAndDeletePerson(person.getAge(), person.getName(), person.getSurname());
    }

    /**
     * Демонстрация проблемы удаления только что созданного объекта в одной транзакции.
     * Показывает, почему для реального "создать и удалить" нужны отдельные транзакции.
     */
    public void demonstrateCreateAndDeleteInSingleTransaction() {
        System.out.println("=== Попытка создать и удалить в одной транзакции ===");
        Session session = HibernateSessionFactoryUtil.getSession();
        session.beginTransaction();
        Person person = new Person(null, 22, "Temp", "User");
        session.persist(person);
        System.out.println("Person persist, id=" + person.getId());

        // Попытка удалить объект сразу после persist
        session.remove(person);
        System.out.println("Вызван session.remove(person)");
        session.getTransaction().commit();
        session.close();

        // Проверяем, что реально осталось в БД
        Person check = loadPerson(person.getId());
        if (check == null) {
            System.out.println("Объект не был сохранён, так как удаление отменило вставку.");
        } else {
            System.out.println("Объект существует, id=" + check.getId());
        }
        System.out.println();
    }
}

