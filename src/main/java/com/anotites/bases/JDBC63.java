package com.anotites.bases;

import java.sql.*;

public class JDBC63 {
    public static void main(String[] args) {

        String dbURL2 = "jdbc:mysql://localhost:3306/ListExpenses";
        String username2 = "root";
        String password2 = "";

        //вывести наибольший платеж за тот день, когда сумма платежей была наибольшей;

        try (Connection myConnection = DriverManager.getConnection(dbURL2, username2, password2);
             Statement statement1 = myConnection.createStatement()) {

            String query1 = "SELECT paydate, MAX(value) AS max_payment\n" +
                    "FROM expenses\n" +
                    "GROUP BY paydate\n" +
                    "HAVING SUM(value) = (\n" +
                    "    SELECT MAX(total)\n" +
                    "    FROM (\n" +
                    "        SELECT SUM(value) AS total\n" +
                    "        FROM expenses\n" +
                    "        GROUP BY paydate\n" +
                    "    ) AS daily_totals\n" +
                    ");";
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

