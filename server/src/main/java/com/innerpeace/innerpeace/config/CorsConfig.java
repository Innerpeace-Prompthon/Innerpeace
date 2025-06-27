package com.innerpeace.innerpeace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://innerpeacebucket.s3-website.ap-northeast-2.amazonaws.com")
                .allowedMethods("POST")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

