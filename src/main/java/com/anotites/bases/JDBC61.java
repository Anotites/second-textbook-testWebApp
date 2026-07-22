package com.anotites.bases;

import java.sql.*;

public class JDBC61 {
    public static void main(String[] args) {

        String dbURL2 = "jdbc:mysql://localhost:3306/ListExpenses";
        String username2 = "root";
        String password2 = "";

        //вывести список получателей платежей, и сумму платежей по каждому из них;

        try (Connection myConnection = DriverManager.getConnection(dbURL2, username2, password2);
             Statement statement1 = myConnection.createStatement()) {

            String query1 = "SELECT SUM(value),name FROM expenses, receivers rs WHERE receiver=rs.num\n" +
                    "GROUP BY name";
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

