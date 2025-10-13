package com.xiaou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xiaou.mapper")
@EnableScheduling
public class LibrarySeatApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibrarySeatApplication.class, args);
        System.out.println("图书馆座位预约系统启动成功！");
        System.out.println("访问地址: http://localhost:8010");
    }
}

