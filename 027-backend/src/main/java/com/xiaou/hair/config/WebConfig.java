package com.xiaou.hair.config;

import com.xiaou.hair.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")  // 拦截所有API请求
                .excludePathPatterns(
                        "/api/auth/register",     // 排除注册
                        "/api/auth/login",        // 排除登录
                        "/api/store/list",        // 排除门店列表（游客可看）
                        "/api/store/{id}",        // 排除门店详情
                        "/api/hairdresser/list",  // 排除理发师列表
                        "/api/hairdresser/{id}",  // 排除理发师详情
                        "/api/service/list",      // 排除服务列表
                        "/api/service/{id}",      // 排除服务详情
                        "/api/service/categories" // 排除服务分类
                );
    }

    /**
     * 配置静态资源映射（前端打包后的文件）
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        
        // 文件上传目录映射
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/");
    }

    /**
     * 配置跨域（前后端分离开发时使用）
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
