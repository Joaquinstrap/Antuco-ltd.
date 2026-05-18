package com.example.antuco.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableWebSecurity // Habilitamos la seguridad aquí mismo
public class UsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuariosApplication.class, args);
    }

    // MÉTODO 1: Para encriptar contraseñas (Soluciona tu error de BCrypt)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // MÉTODO 2: Para desbloquear tus rutas de Auth (Login/Registro)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivamos protección CSRF para APIs
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso sin token a estas rutas
                .requestMatchers("/api/V1/auth/**").permitAll() 
                // Cualquier otra ruta requeriría autenticación (por ahora no la usamos)
                .anyRequest().authenticated() 
            );

        return http.build();
    }
}