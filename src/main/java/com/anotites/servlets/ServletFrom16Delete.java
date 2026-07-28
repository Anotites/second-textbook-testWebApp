package com.anotites.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServletFrom16Delete extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/deleteJSP.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String number = request.getParameter("num");
        ServletConfig conf = getServletConfig();
        String dbURL = conf.getInitParameter("dbURL");
        String username = conf.getInitParameter("username");
        String password = conf.getInitParameter("password");
//        StringBuilder s = new StringBuilder();
        List<String[]> rows = new ArrayList<>();
        boolean k = true;
        int newNumberForDelete;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер MySQL не найден", e);
        }
        String query = "SELECT expenses.num,value,name,paydate FROM expenses, receivers rs WHERE receiver=rs.num and expenses.num=?;";
        int newNumber = Integer.parseInt(number);
        try (Connection myConnection = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement pstmt = myConnection.prepareStatement(query)) {

            pstmt.setInt(1, newNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    k = false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        boolean numMissing = (number.isEmpty() || k);

        if (numMissing) {
            request.getRequestDispatcher("/WEB-INF/NoData.jsp").forward(request, response);
        } else {
            try (Connection myConnection = DriverManager.getConnection(dbURL, username, password);
                 Statement statement = myConnection.createStatement()) {
                String sql = "DELETE FROM expenses WHERE expenses.num = ?";
                try (PreparedStatement pstmt = myConnection.prepareStatement(sql)) {
                    newNumberForDelete = Integer.parseInt(number);
                    pstmt.setInt(1, newNumberForDelete);
                    pstmt.executeUpdate();
                }
                String queryForShow = "SELECT expenses.num,value,name,paydate FROM expenses, receivers rs WHERE receiver=rs.num;";
                ResultSet result = statement.executeQuery(queryForShow);

                while (result.next()) {
                    rows.add(new String[]{
                            result.getString("num"),
                            result.getString("name"),
                            result.getString("value"),
                            result.getString("paydate")
                    });
                }
//                StringBuilder d = new StringBuilder();
//                d.append("num").append(" ").append("value").append(" ").append("name")
//                        .append(" ").append("paydate").append("<br>");
//                while (result.next()) {
//                    s.append(result.getString(1)).append(" ").append(result.getString(2))
//                            .append(" ").append(result.getString(3)).append(" ").append(result.getString(4)).append("<br>");
//                }
//                s = d.append(s);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("number", number);
            request.setAttribute("rows", rows);
//            request.setAttribute("table", s);
            request.getRequestDispatcher("/WEB-INF/resultForDelete.jsp").forward(request, response);
        }
    }
}

