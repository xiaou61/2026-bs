package com.programming.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 编程学习交流平台 - 主启动类
 */
@SpringBootApplication
@MapperScan("com.programming.learning.mapper")
@EnableCaching
@EnableAsync
@EnableScheduling
public class ProgrammingLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgrammingLearningApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("编程学习交流平台启动成功！");
        System.out.println("API地址: http://localhost:8037/api");
        System.out.println("API文档: http://localhost:8037/api/doc.html");
        System.out.println("Druid监控: http://localhost:8037/api/druid");
        System.out.println("========================================\n");
    }
}
