package com.anotites.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

public class ServletFrom11 extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userAgent = request.getHeader("User-Agent");
        String browser;
        if (userAgent == null) {
            browser = "неизвестный";
        } else if (userAgent.contains("Firefox")) {
            browser = "Firefox";
        } else if (userAgent.contains("Edg")) {
            browser = "Edge";
        } else if (userAgent.contains("Chrome") && !userAgent.contains("Edg")) {
            browser = "Chrome";
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome") && !userAgent.contains("Edg")) {
            browser = "Safari";
        } else {
            browser = "неизвестный";
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>First Servlet</title></head>");
        out.println("<body><h1>Приветствую пользователя " + browser + "</h1>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}

