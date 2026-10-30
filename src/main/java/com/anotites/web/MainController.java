package com.anotites.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home() {
        System.out.println("=== Контроллер home() вызван! ==="); // ДОБАВИТЬ ВОТ ЭТУ СТРОКУ
        return "home"; // соответствует файлу templates/home.html
    }

    @GetMapping("/about")
    // Защита на уровне метода! Требуем конкретное ПРАВО (authority)
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public String about() {
        return "about"; // соответствует файлу templates/about.html
    }
}
