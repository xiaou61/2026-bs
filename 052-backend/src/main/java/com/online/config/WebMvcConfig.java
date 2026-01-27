package com.online.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/user/login",
                        "/api/user/register",
                        "/api/admin/login",
                        "/api/banner/list",
                        "/api/category/list",
                        "/api/course/list",
                        "/api/course/recommend",
                        "/api/course/hot",
                        "/api/course/latest",
                        "/api/course/*",
                        "/api/course/*/chapters",
                        "/api/comment/list"
                );
    }
}
