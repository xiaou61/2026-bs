package com.xiaou.hair;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 线上理发预约系统启动类
 */
@SpringBootApplication
@MapperScan("com.xiaou.hair.mapper")
@EnableScheduling
public class HairSalonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairSalonApplication.class, args);
        System.out.println("=================================");
        System.out.println("线上理发预约系统启动成功！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("=================================");
    }
}
