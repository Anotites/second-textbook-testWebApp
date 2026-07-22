package com.anotites.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

public class FirstServletFrom9 extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int count;
    private static final String FILE_PATH = System.getProperty("user.home") + "/count.txt";

    @Override
    public void init() {
        // читаем count из файла при старте
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            count = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            count = 0; // если файла нет или он пуст
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        synchronized (this) {
            count++;
        }
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            writer.print(count);
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Second Servlet</title></head>");
        out.println("<body><h1>This is Second Servlet</h1>");
        out.println("<body><h1>Количество посещений сервлета: " + count + "</h1>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}

