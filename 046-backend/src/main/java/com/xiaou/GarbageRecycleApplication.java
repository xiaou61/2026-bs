package com.xiaou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.mapper")
public class GarbageRecycleApplication {
    public static void main(String[] args) {
        SpringApplication.run(GarbageRecycleApplication.class, args);
        System.out.println("垃圾回收服务系统启动成功！");
        System.out.println("接口文档：http://localhost:8046/doc.html");
        System.out.println("H2控制台：http://localhost:8046/h2-console");
    }
}
