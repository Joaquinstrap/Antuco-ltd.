package com.example.antuco.inventario.Config; // Ajusta el package al de él

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Esto permite que el servicio de pedidos entre sin clave
                .requestMatchers("/api/V1/inventario/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}