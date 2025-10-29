package com.xiaou.ordering.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/index");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/register").setViewName("user/register");
        registry.addViewController("/merchant/login").setViewName("merchant/login");
        registry.addViewController("/admin/login").setViewName("admin/login");
    }
}

