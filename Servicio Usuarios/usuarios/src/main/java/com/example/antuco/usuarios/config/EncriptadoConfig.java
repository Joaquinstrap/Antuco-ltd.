package com.example.antuco.usuarios.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.antuco.usuarios.service.NuevoUserDetailService;

@Configuration
public class EncriptadoConfig {
        
    @Autowired
    private NuevoUserDetailService userDetailsService;

    // BCrypt sirve para encriptar las claves de usuario
    // Le ingresas un dato y lo convierte en un hash, para no tener el dato expuesto
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This bean hooks up your UserDetailsService and PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for API testing
            .formLogin(form -> form.disable()) 
            .httpBasic(basic -> basic.disable())
        
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**", "/error").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }

}
