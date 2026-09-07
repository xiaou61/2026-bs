package com.xiaou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.mapper")
public class NursingHomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NursingHomeApplication.class, args);
        System.out.println("=====================================");
        System.out.println("养老院管理系统启动成功！");
        System.out.println("接口文档: http://localhost:8045/doc.html");
        System.out.println("=====================================");
    }
}
