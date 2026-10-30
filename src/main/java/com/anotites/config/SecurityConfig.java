package com.anotites.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // Включаем веб-безопасность
@EnableMethodSecurity // Включаем защиту на уровне методов (для @PreAuthorize)
public class SecurityConfig {
    // 1. Правила доступа к URL-адресам
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Используем AntPathRequestMatcher, чтобы обойти ошибку Servlet 3.0
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()

                        // Страница "О нас" требует роль ADMIN
                        .requestMatchers(new AntPathRequestMatcher("/about")).hasRole("ADMIN")

                        // Все остальные страницы требуют просто факта логина
                        .anyRequest().authenticated()
                )
                // Включаем стандартную форму логина Spring (генерируется автоматически)
                .formLogin(Customizer.withDefaults())
                // Включаем базовую аутентификацию (нужно для Postman/тестов)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // 2. Создаем пользователей в памяти (для учебных целей)
    @Bean
    public UserDetailsService userDetailsService() {
        // Пользователь 1: Роль USER и право READ_PRIVILEGE
        UserDetails user = User.withUsername("user")
                .password("{noop}user123")
                // Передаем всё сразу в authorities. Роль должна быть с префиксом ROLE_
                .authorities("ROLE_USER", "READ_PRIVILEGE")
                .build();

        // Пользователь 2: Роль ADMIN, а также права READ и WRITE
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                // Передаем всё сразу в authorities
                .authorities("ROLE_ADMIN", "ROLE_USER", "READ_PRIVILEGE", "WRITE_PRIVILEGE")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
