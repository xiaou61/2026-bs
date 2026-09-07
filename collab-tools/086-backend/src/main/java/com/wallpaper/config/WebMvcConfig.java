package com.wallpaper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/login",
                        "/api/auth/register",
                        "/api/category/enabled",
                        "/api/tag/enabled",
                        "/api/wallpaper/public/list",
                        "/api/wallpaper/detail/**",
                        "/api/wallpaper/recommend",
                        "/api/wallpaper/download/**",
                        "/api/banner/enabled",
                        "/api/notice/public/list"
                );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String filePath = new File(uploadDir).getAbsolutePath();
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + filePath + "/");
    }
}
