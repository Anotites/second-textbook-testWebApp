package com.anotites.service;

import com.anotites.pojos.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PersonServiceTest {

    private static PersonService personService;

    @BeforeAll
    static void setUp() {
        // Предполагаем, что HibernateSessionFactoryUtil уже инициализирован
        // Можно явно вызвать что-то, если нужно
        personService = new PersonService();
    }

    @Test
    void testDeletePerson() {
        // 1. Создаём и сохраняем нового человека
        Person saved = personService.savePerson(null, 25, "Test", "User");
        Integer id = saved.getId();

        // 2. Удаляем
        personService.deletePerson(id);

        // 3. Проверяем, что запись удалена
        Person deleted = personService.loadPerson(id);
        assertNull(deleted, "Person should be deleted");
    }

    @Test
    void testSavePerson() {
        // 1. Создаём и сохраняем нового человека
        Person saved = personService.savePerson(null, 25, "Test", "User");
        Integer id = saved.getId();

        // 2. Выгружаем
        Person loaded = personService.loadPerson(id);

        // 3. Проверяем, что запись создана
        String name = loaded.getName();
        String surname = loaded.getSurname();
        Integer age = loaded.getAge();
        assertEquals("Test", name);
        assertEquals("User", surname);
        assertEquals(25, age);
    }

    @Test
    void testLoadPerson() {
        // 1.  Выгружаем
        Person saved = personService.savePerson(null, 25, "Test", "User");
        Integer id = saved.getId();
        Person loaded = personService.loadPerson(id);

        // 2. Проверяем, что запись выгружена
        String name = loaded.getName();
        String surname = loaded.getSurname();
        Integer age = loaded.getAge();
        assertEquals("Test", name);
        assertEquals("User", surname);
        assertEquals(25, age);
    }
}
