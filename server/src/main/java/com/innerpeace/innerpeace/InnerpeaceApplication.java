package com.innerpeace.innerpeace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class InnerpeaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnerpeaceApplication.class, args);
    }

    /**
     * CORS 설정 - 프론트엔드에서 백엔드 API 호출을 위해 필요
     * 개발 환경에서만 사용하고, 운영 환경에서는 보안을 위해 제한적으로 설정해야 함
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")  // 패턴 사용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(false)  // credentials를 false로 설정
                        .maxAge(3600);
            }
        };
    }
}