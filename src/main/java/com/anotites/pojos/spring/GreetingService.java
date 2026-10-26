package com.anotites.pojos.spring;

public class GreetingService {
    private String greeting;

    // Spring будет использовать этот сеттер, если мы укажем property в XML
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void sayHello() {
        System.out.println("Spring says: " + greeting);
    }

    public static GreetingService getInstance() {
        return new GreetingService();
    }

    public void init() {
        System.out.println("Initialisation");
    }

    public void destroy() {
        System.out.println("Destruction");
    }
}
