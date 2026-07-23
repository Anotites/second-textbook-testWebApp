package com.anotites.servlets;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ServletFrom12 extends HttpServlet {
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

        response.setContentType("image/jpeg");
        BufferedImage image = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(new Font("Serif", Font.ITALIC, 30));
        graphics.setColor(Color.green);
        graphics.drawString("Количество посещений сервлета: " + count, 10, 60);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpeg", out);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}

