package com.example.antuco.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableWebSecurity
public class AutenticacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutenticacionApplication.class, args);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso a las rutas de autenticación
                .requestMatchers("/api/V1/autenticacion/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}