package com.anotites.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

public class ServletFrom10 extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>My 3 Servlet</title></head><body>");
        out.println("<form method='post' action=''>");
        out.println("Введите имя:<input name='fio'><br>");
        out.println("Введите телефон:<input name='phone'><br>");
        out.println("Введите адрес:<input name='email'><br>");
        out.println("<input type='submit'>");
        out.println("</form></body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("fio");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        boolean nameMissing = (name == null || name.isEmpty());
        boolean phoneMissing = (phone == null || phone.isEmpty());
        boolean emailMissing = (email == null || email.isEmpty());

        if (nameMissing || (phoneMissing && emailMissing)) {
            out.println("<html><head><title>Error</title></head>");
            out.println("<body><h1>" + "Вы ввели не все необходимые данные" + "</h1>");
            out.println("<a href=''>Назад</a>");
            out.println("</body></html>");
        } else {
            out.println("<html><head><title>My 3 Servlet</title></head>");
            out.println("<body><h1>" + name + "</h1>");
            out.println("<h1>" + phone + "</h1>");
            out.println("<h1>" + email + "</h1>");
            out.println("</body></html>");
        }
    }
}

