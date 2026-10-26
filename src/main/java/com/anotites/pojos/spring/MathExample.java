package com.anotites.pojos.spring;

public class MathExample {
    Double i;
    Double square;
    boolean logic;
    boolean rel;
    String example;

    public void setI(Double i) {
        this.i = i;
    }

    public void setSquare(Double square) {
        this.square = square;
    }

    public void setLogic(boolean logic) {
        this.logic = logic;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void sayName() {
        System.out.println("Spring says: " + i);
        System.out.println("square: " + square);
        System.out.println("logic: " + logic);
        System.out.println("rel: " + rel);
        System.out.println("example: " + example);
    }

    public static MathExample getInstance() {
        return new MathExample();
    }
}
