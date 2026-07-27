package com.anotites.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;
import java.sql.*;

public class ServletFrom16Insert extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/insertJSP.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String number = request.getParameter("num");
        String valueS = request.getParameter("value");
        String paydate = request.getParameter("paydate");
        String receiverS = request.getParameter("receiver");

        boolean numberMissing = (number == null || number.isEmpty());
        boolean valueMissing = (valueS == null || valueS.isEmpty());
        boolean paydateMissing = (paydate == null || paydate.isEmpty());
        boolean receiverMissing = (receiverS == null || receiverS.isEmpty());

        if (numberMissing || valueMissing || paydateMissing || receiverMissing) {
            request.getRequestDispatcher("/WEB-INF/NoAllData.jsp").forward(request, response);
        } else {
            int num = Integer.parseInt(number);
            double value = Double.parseDouble(valueS);
            int receiver = Integer.parseInt(receiverS);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Error loading driver: " + cnfe);
            }
            ServletConfig conf = getServletConfig();
            String dbURL = conf.getInitParameter("dbURL");
            String username = conf.getInitParameter("username");
            String password = conf.getInitParameter("password");
            String query = "INSERT IGNORE INTO expenses (num,paydate,value,receiver) " + "VALUES(?,?,?,?);";

            try (Connection myConnection2 = DriverManager.getConnection(dbURL, username, password);
                 PreparedStatement pStatement = myConnection2.prepareStatement(query)) {
                pStatement.setInt(1, num);
                pStatement.setString(2, paydate);
                pStatement.setDouble(3, value);
                pStatement.setInt(4, receiver);
                pStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        request.setAttribute("number", number);
        request.getRequestDispatcher("/WEB-INF/resultForInsert.jsp").forward(request, response);
    }
}


