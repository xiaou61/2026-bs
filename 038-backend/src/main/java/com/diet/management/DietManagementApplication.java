package com.diet.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 个人饮食管理系统 - 主启动类
 */
@SpringBootApplication
@MapperScan("com.diet.management.mapper")
@EnableCaching
@EnableAsync
@EnableScheduling
public class DietManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietManagementApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("个人饮食管理系统启动成功！");
        System.out.println("API地址: http://localhost:8038/api");
        System.out.println("API文档: http://localhost:8038/api/doc.html");
        System.out.println("Druid监控: http://localhost:8038/api/druid");
        System.out.println("========================================\n");
    }
}
