package com.senasa.bpm.ng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*"); // Permitir todos los dominios
        corsConfiguration.setAllowCredentials(true); // Permitir credenciales (cookies, headers de autenticación, etc.)
        corsConfiguration.addAllowedHeader("*"); // Permitir todos los headers
        corsConfiguration.addAllowedMethod("*"); // Permitir todos los métodos (GET, POST, PUT, DELETE, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Aplicar la configuración a todas las rutas
        return new CorsFilter(source);
    }
}
