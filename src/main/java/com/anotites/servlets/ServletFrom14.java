package com.anotites.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.*;

public class ServletFrom14 extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ServletConfig conf = getServletConfig();
        String dbURL = conf.getInitParameter("dbURL");
        String username = conf.getInitParameter("username");
        String password = conf.getInitParameter("password");
        StringBuilder s = new StringBuilder();

        //вывести список получателей платежей, и сумму платежей по каждому из них;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер MySQL не найден", e);
        }

        try (Connection myConnection = DriverManager.getConnection(dbURL, username, password);
             Statement statement = myConnection.createStatement()) {

            String query = "SELECT value,name,paydate FROM expenses, receivers rs WHERE receiver=rs.num;";
            ResultSet result = statement.executeQuery(query);

            StringBuilder d = new StringBuilder();
            d.append("value").append(" ").append("name")
                    .append(" ").append("paydate").append("<br>");
            while (result.next()) {
                s.append(result.getString(1)).append(" ").append(result.getString(2))
                        .append(" ").append(result.getString(3)).append("<br>");
            }
            s = d.append(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>14 Servlet</title></head>");
        out.println("<body><h1>Платежи:</h1>");
        out.println("<p>" + s + "</p>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}

