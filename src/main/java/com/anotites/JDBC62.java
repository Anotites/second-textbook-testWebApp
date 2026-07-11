package com.anotites;

import java.sql.*;

public class JDBC62 {
    public static void main(String[] args) {

        String dbURL2 = "jdbc:mysql://localhost:3306/ListExpenses";
        String username2 = "root";
        String password2 = "";

        //вывести сумму платежей за тот день, когда был наибольший платеж;

        try (Connection myConnection = DriverManager.getConnection(dbURL2, username2, password2);
             Statement statement1 = myConnection.createStatement()) {

            String query1 = "SELECT SUM(value),paydate FROM expenses " +
                    "WHERE paydate = (SELECT paydate FROM expenses " +
                    "WHERE value=(SELECT MAX(value) FROM expenses)) GROUP BY paydate";
            ResultSet result = statement1.executeQuery(query1);

            while (result.next()) {
                System.out.println(result.getString(1) + " "
                        + result.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

