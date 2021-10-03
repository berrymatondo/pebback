package com.peb.pebb.securityConfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/**").allowedOrigins("http://localhost:3000", "https://pebback.herokuapp.com")
                        .allowedMethods("GET", "PUT", "DELETE", "POST").allowedHeaders("*");
            }
        };
    }

}
