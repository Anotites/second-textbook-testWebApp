package com.anotites.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component // Чтобы сканер компонентов нашел класс
@Aspect    // Чтобы Spring обработал аннотации внутри класса
public class LoggingAspect {
    @Before("execution(* com.anotites.service..*.*(..))")
    public void logBefore() {
        System.out.println("--- Выполняется ПЕРЕД вызовом метода ---");
    }

    @After("execution(* com.anotites.service..*.*(..))")
    public void logAfter() {
        System.out.println("--- Выполняется ПОСЛЕ вызова метода ---");
    }

    @Around("execution(* com.anotites.service..*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("--- Оборачивает: ДО метода ---");
        Object result = joinPoint.proceed(); // Запускаем сам метод
        System.out.println("--- Оборачивает: ПОСЛЕ метода ---");
        return result;
    }

    @AfterThrowing(pointcut = "execution(* com.anotites.service..*.*(..))", throwing = "ex")
    public void logAfterThrowing(Throwable ex) {
        System.out.println("--- Выполняется если есть исключение: " + ex.getMessage() + " ---");
    }

    @AfterReturning("execution(* com.anotites.service..*.*(..))")
    public void logAfterReturning() {
        System.out.println("--- Выполняется ПОСЛЕ успешного выполнения метода ---");
    }
}
