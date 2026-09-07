package com.xiaou.ordering.config;

import com.xiaou.ordering.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/index");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/register").setViewName("user/register");
        registry.addViewController("/user/cart").setViewName("user/cart");
        registry.addViewController("/user/orders").setViewName("user/orders");
        registry.addViewController("/merchant/login").setViewName("merchant/login");
        registry.addViewController("/merchant/manage").setViewName("merchant/manage");
        registry.addViewController("/admin/login").setViewName("admin/login");
        registry.addViewController("/admin/dashboard").setViewName("admin/dashboard");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/user/register",
                        "/api/auth/user/login",
                        "/api/auth/merchant/login",
                        "/api/auth/admin/login"
                );
    }
}

