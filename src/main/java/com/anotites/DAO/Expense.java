package com.anotites.DAO;

public class Expense {
    private int num;
    private String paydate;
    private int receiver;
    private double value;

    public Expense(int num, String paydate, int receiver, double value) {
        this.num = num;
        this.paydate = paydate;
        this.receiver = receiver;
        this.value = value;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "num=" + num +
                ", paydate='" + paydate + '\'' +
                ", receiver=" + receiver +
                ", value=" + value +
                '}';
    }
}
