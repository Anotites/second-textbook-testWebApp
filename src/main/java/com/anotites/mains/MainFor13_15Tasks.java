package com.anotites.mains;

import com.anotites.config.DatabaseConfig;
import com.anotites.pojos.Person;
import com.anotites.service.PersonServiceNew;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainFor13_15Tasks {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

        PersonServiceNew service = context.getBean(PersonServiceNew.class);
        Person person = new Person(null, 31, "Olga", "Smirnova");
        service.createPerson(person);

        System.out.println("Сохранён Person с ID: " + person.getId());
    }
}
