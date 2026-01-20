package com.xiaou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.mapper")
public class HomestayBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomestayBookingApplication.class, args);
        System.out.println("=====================================");
        System.out.println("特色民宿预订平台启动成功！");
        System.out.println("接口文档: http://localhost:8080/doc.html");
        System.out.println("=====================================");
    }
}
