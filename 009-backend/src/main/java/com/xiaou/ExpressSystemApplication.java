package com.xiaou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExpressSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpressSystemApplication.class, args);
        System.out.println("=================================");
        System.out.println("校园快递代收管理系统启动成功！");
        System.out.println("访问地址: http://localhost:8009");
        System.out.println("=================================");
    }
}

