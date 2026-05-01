package org.example.aihomeworkgrading.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有路径
        registry.addMapping("/**")
                // 允许所有来源（使用 Pattern 模式，解决 credentials 冲突）
                .allowedOriginPatterns("*")
                // 允许所有请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许所有请求头
                .allowedHeaders("*")
                // 允许携带认证信息（Cookie等）
                .allowCredentials(true)
                // 预检请求有效期（1小时）
                .maxAge(3600);
    }
}
