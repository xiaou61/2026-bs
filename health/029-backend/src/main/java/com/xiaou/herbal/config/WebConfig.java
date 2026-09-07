package com.xiaou.herbal.config;

import com.xiaou.herbal.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/v1/user/register",
                        "/api/v1/user/login",
                        "/api/v1/recipe/list",
                        "/api/v1/recipe/*/detail",
                        "/api/v1/recipe/search",
                        "/api/v1/topic/list",
                        "/api/v1/topic/*/detail",
                        "/api/v1/ingredient/*/detail",
                        "/api/v1/comment/list/**"
                );
    }
}
