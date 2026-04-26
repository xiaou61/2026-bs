package com.xiaou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.mapper")
public class MuseumRelicApplication {
    public static void main(String[] args) {
        SpringApplication.run(MuseumRelicApplication.class, args);
        System.out.println("博物馆文物数字化管理平台启动成功：http://localhost:8048/doc.html");
        System.out.println("H2 控制台：http://localhost:8048/h2-console");
    }
}
