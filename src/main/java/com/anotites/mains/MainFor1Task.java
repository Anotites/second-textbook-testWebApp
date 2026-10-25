package com.anotites.mains;

import com.anotites.pojos.spring.GreetingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainFor1Task {
    public static void main(String[] args) {
        // 1. Загружаем контекст из XML в classpath
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 2. Получаем бин из контекста
        GreetingService service = context.getBean("greetingService", GreetingService.class);

        // 3. Вызываем метод
        service.sayHello();
    }
}
