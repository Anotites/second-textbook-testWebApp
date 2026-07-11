package com.anotites.DAO;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Dao myDao = MyDao.getMyDao();
        System.out.println(myDao.getReceiver(3).toString());
        System.out.println("***************************************");
        ArrayList<Receiver> receivers = myDao.getReceivers();
        for (Receiver rec : receivers) System.out.println(rec.toString());
        System.out.println("***************************************");
        System.out.println(myDao.getExpense(5).toString());
        System.out.println("***************************************");
        ArrayList<Expense> expenses = myDao.getExpenses();
        for (Expense exp : expenses) System.out.println(exp.toString());
        System.out.println("***************************************");
        Receiver r = new Receiver(10, "Гunepмapкem proStore");
        System.out.println(myDao.addReceiver(r));
        System.out.println("***************************************");
        Expense e = new Expense(6, "2025-05-05", 3, 900);
        System.out.println(myDao.addExpense(e));
        System.out.println("***************************************");
    }
}
