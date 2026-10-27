package com.anotites.service;

import org.springframework.stereotype.Component;

@Component("newServiceForSpring")
public class NewServiceForSpring {

    public void newMethod() {
        System.out.println("Сам метод");
    }

    public void newSecondMethod(int i) {
        System.out.println("Результат: " + 100 / i);
    }
}
