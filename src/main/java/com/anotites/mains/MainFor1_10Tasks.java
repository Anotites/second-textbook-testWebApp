package com.anotites.mains;

import com.anotites.pojos.spring.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainFor1_10Tasks {
    public static void main(String[] args) {
        // Загружаем контекст из XML в classpath
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Получаем бин из контекста
        GreetingService service = context.getBean("greetingService", GreetingService.class);
        GreetingService defaultService = context.getBean("defaultGreetingService", GreetingService.class);
        GreetingService pGreetingService = context.getBean("pGreetingService", GreetingService.class);
        Person person = context.getBean("person", Person.class);
        NewPerson newPerson = context.getBean("newPerson", NewPerson.class);
        NewPerson newPersonWithoutNull = context.getBean("newPersonWithoutNull", NewPerson.class);
        MathExample mathExample = context.getBean("mathExample", MathExample.class);
        PersonFor7 personByName = context.getBean("personByName", PersonFor7.class);
        PersonFor7 personNo = context.getBean("personNo", PersonFor7.class);
        PersonFor7 personByType = context.getBean("personByType", PersonFor7.class);
        PersonFor7 personConstructor = context.getBean("personConstructor", PersonFor7.class);
        PersonFor8 personAnnotation = context.getBean("personAnnotation", PersonFor8.class);
        PersonFor9 personAnnotationQualifier = context.getBean("personAnnotationQualifier", PersonFor9.class);


        // Вызываем метод
        service.sayHello();
        defaultService.sayHello();
        pGreetingService.sayHello();
        person.sayName();
        newPerson.sayName();
        newPersonWithoutNull.sayName();
        mathExample.sayName();
        personByName.printInfo();
        personNo.printInfo();
        personByType.printInfo();
        personConstructor.printInfo();
        personAnnotation.printInfo();
        personAnnotationQualifier.setName("Lena"); // Добавьте эту строку
        personAnnotationQualifier.printInfo();

        // Закрываем контекст, чтобы вызвались destroy-методы
        context.close();
    }
}
