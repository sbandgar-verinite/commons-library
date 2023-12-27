package com.verinite.commons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //enables CORS requests from any origin to any endpoint in the application
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE","OPTIONS");
            }
        };
    }
}
