package com.anotites.DAO;

import java.sql.*;
import java.util.ArrayList;

public class MyDao implements Dao {

    //Создайте объект DAO на основе интерфейса, приведенного выше.
    //Также необходимо создать классы Receiver и Expense, свойства которых
    //соответствуют полям таблиц базы данных расходов. Поле дата в классах можно хранить
    //в виде строки.

    private static MyDao instance;

    private MyDao() {
    }

    public static MyDao getMyDao() {
        if (instance == null) {
            instance = new MyDao();
        }
        return instance;
    }

    String dbURL = "jdbc:mysql://localhost:3306/ListExpenses";
    String username = "root";
    String password = "";

    @Override
    public Receiver getReceiver(int num) {
        Receiver receiver = null;
        try (Connection myConnection = DriverManager.getConnection(dbURL, username, password);
             Statement statement = myConnection.createStatement()) {
            String query = "SELECT DISTINCT receivers.num, receivers.name " +
                    "FROM receivers WHERE receivers.num=" + num + ";";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                receiver = new Receiver(result.getInt(1), result.getString(2));
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return receiver;
    }

    @Override
    public ArrayList<Receiver> getReceivers() {
        ArrayList<Receiver> receivers;
        Receiver receiver;
        try (Connection myConnection = DriverManager.getConnection(dbURL, username, password);
             Statement statement = myConnection.createStatement()) {
            String query = "SELECT * FROM receivers;";
            ResultSet result = statement.executeQuery(query);
            receivers = new ArrayList<>();
            while (result.next()) {
                receiver = new Receiver(result.getInt(1), result.getString(2));
                receivers.add(receiver);
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return receivers;
    }

    @Override
    public Expense getExpense(int num) {
        Expense expense = null;
        try (Connection myConnection = DriverManager.getConnection(dbURL, username, password);
             Statement statement = myConnection.createStatement()) {
            String query = "SELECT DISTINCT expenses.num, expenses.paydate, expenses.receiver, expenses.value " +
                    "FROM expenses WHERE expenses.num=" + num + ";";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                expense = new Expense(result.getInt(1), result.getString(2),
                        result.getInt(3), result.getDouble(4));
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return expense;
    }

    @Override
    public ArrayList<Expense> getExpenses() {
        ArrayList<Expense> expenses;
        Expense expense;
        try (Connection myConnection = DriverManager.getConnection(dbURL, username, password);
             Statement statement = myConnection.createStatement()) {
            String query = "SELECT * FROM expenses;";
            ResultSet result = statement.executeQuery(query);
            expenses = new ArrayList<>();
            while (result.next()) {
                expense = new Expense(result.getInt(1), result.getString(2), result.getInt(3), result.getDouble(4));
                expenses.add(expense);
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return expenses;
    }

    @Override
    public int addReceiver(Receiver receiver) {
        try (Connection myConnection = DriverManager.getConnection(dbURL, username, password)) {
            String query4 = "INSERT IGNORE INTO receivers (num, name) VALUES(?,?);";
            PreparedStatement pStatement1 = myConnection.prepareStatement(query4);
            pStatement1.setInt(1, receiver.getNum());
            pStatement1.setString(2, receiver.getName());
            pStatement1.executeUpdate();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return receiver.getNum();
    }

    @Override
    public int addExpense(Expense expense) {
        try (Connection myConnection = DriverManager.getConnection(dbURL, username, password)) {
            String query4 = "INSERT INTO expenses (num, paydate, receiver, value) VALUES(?,?,?,?);";
            PreparedStatement pStatement1 = myConnection.prepareStatement(query4);
            pStatement1.setInt(1, expense.getNum());
            pStatement1.setString(2, expense.getPaydate());
            pStatement1.setInt(3, expense.getReceiver());
            pStatement1.setDouble(4, expense.getValue());
            pStatement1.executeUpdate();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return expense.getNum();
    }
}
