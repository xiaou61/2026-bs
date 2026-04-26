package com.rental.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

/**
 * MyBatis-Plus配置类
 */
@Configuration
public class MyBatisPlusConfig {

    @Value("${app.mybatis.db-type:H2}")
    private String dbType;

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(resolveDbType()));
        return interceptor;
    }

    private DbType resolveDbType() {
        try {
            return DbType.valueOf(dbType.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return DbType.H2;
        }
    }
}
