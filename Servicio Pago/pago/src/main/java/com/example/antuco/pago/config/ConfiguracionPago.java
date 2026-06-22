package com.example.antuco.pago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfiguracionPago {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
