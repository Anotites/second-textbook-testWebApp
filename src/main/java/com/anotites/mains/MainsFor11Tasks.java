package com.anotites.mains;

import com.anotites.service.NewServiceForSpring;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainsFor11Tasks {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Получаем бин из контекста
        NewServiceForSpring newServiceForSpring = context.getBean("newServiceForSpring", NewServiceForSpring.class);

        newServiceForSpring.newMethod();
        try {
            newServiceForSpring.newSecondMethod(0);
        } catch (ArithmeticException e) {
            System.out.println("Исключение перехвачено в main");
        }
        context.close();
    }
}
