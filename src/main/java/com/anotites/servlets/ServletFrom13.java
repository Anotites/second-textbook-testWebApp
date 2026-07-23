package com.anotites.servlets;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServletFrom13 extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int count;
    private static final String FILE_PATH = System.getProperty("user.home") + "/count.txt";
    private static final String USER_COOKIE_NAME = "uniqueVisitorId";
    private final ConcurrentHashMap<LocalDate, Set<String>> visitorTracker = new ConcurrentHashMap<>();


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

        String userId = getUserIdFromCookies(request);

        if (userId == null) {
            userId = UUID.randomUUID().toString();
            Cookie userCookie = new Cookie(USER_COOKIE_NAME, userId);
            userCookie.setMaxAge(60 * 60 * 24 * 365); // Кука живет 1 год
            userCookie.setPath("/");
            response.addCookie(userCookie);
        }

        // Учитываем посетителя за сегодня
        LocalDate today = LocalDate.now();
        Set<String> todayVisitors = visitorTracker.computeIfAbsent(today, k -> ConcurrentHashMap.newKeySet());

        // Если пользователь уникален для сегодня, добавляем его в множество
        boolean isNewUniqueVisitor = todayVisitors.add(userId);

        // Выводим результат
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Счетчик уникальных посетителей</title></head><body>");
        out.println("<h1>Уникальных посетителей сегодня: " + todayVisitors.size() + "</h1>");
        if (isNewUniqueVisitor) {
            out.println("<p>Вы - новый уникальный посетитель сегодня!</p>");
            synchronized (this) {
                count++;
            }
            try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
                writer.print(count);
            }
        } else {
            out.println("<p>Вы уже посещали сайт сегодня.</p>");
        }
        out.println("<p>Количество посещений сервлета всего:" + count + "</p>");
        out.println("<p><a href=''>Обновить страницу</a></p>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }

    private String getUserIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (USER_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

