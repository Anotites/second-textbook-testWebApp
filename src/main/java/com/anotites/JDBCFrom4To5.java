package com.anotites;

import java.sql.*;

public class JDBCFrom4To5 {
    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Usage: java myNewConnection <num> <paydate(yyyy-MM-dd)> <value> <receiverId>");
            return;
        }

        int num = Integer.parseInt(args[0]);
        String paydate = args[1];
        double value = Double.parseDouble(args[2]);
        int receiver = Integer.parseInt(args[3]);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Error loading driver: " + cnfe);
        }
        String dbURL = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";

        try (Connection myConnection1 = DriverManager.getConnection(dbURL, username, password); Statement statement1 = myConnection1.createStatement()) {
            String query1 = "CREATE DATABASE IF NOT EXISTS ListExpenses;";
            statement1.executeUpdate(query1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String dbURL2 = "jdbc:mysql://localhost:3306/ListExpenses";
        String username2 = "root";
        String password2 = "";

        try (Connection myConnection2 = DriverManager.getConnection(dbURL2, username2, password2); Statement statement2 = myConnection2.createStatement()) {
            String query2 = "CREATE TABLE IF NOT EXISTS expenses(num int,paydate date,receiver int,value dec);";
            statement2.executeUpdate(query2);

            String query3 = "CREATE TABLE IF NOT EXISTS receivers(num int,name varchar(255));";
            statement2.executeUpdate(query3);

//            String query4 = "INSERT IGNORE INTO receivers (num, name) VALUES(1,'Вася');";
            String query4 = "INSERT IGNORE INTO receivers (num, name) VALUES(?,?);";
            PreparedStatement pStatement1 = myConnection2.prepareStatement(query4);
            pStatement1.setInt(1, 3);
            pStatement1.setString(2, "Леша");
            pStatement1.executeUpdate();

//            String query5 = "INSERT IGNORE INTO expenses (num,paydate,value,receiver) " +
//                    "VALUES(" + num + ",'" + paydate + "'," + value + "," + receiver + ");";
            String query5 = "INSERT IGNORE INTO expenses (num,paydate,value,receiver) " + "VALUES(?,?,?,?);";
            PreparedStatement pStatement2 = myConnection2.prepareStatement(query5);
            pStatement2.setInt(1, num);
            pStatement2.setString(2, paydate);
            pStatement2.setDouble(3, value);
            pStatement2.setInt(4, receiver);
            pStatement2.executeUpdate();

            String queryLast = "SELECT expenses.num, expenses.paydate, receivers.name, expenses.value " + "FROM expenses, receivers WHERE expenses.receiver = receivers.num";
            ResultSet result = statement2.executeQuery(queryLast);

            while (result.next()) {
                System.out.println(result.getString(1) + " " + result.getString(2) + " " + result.getString(3) + " " + result.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

