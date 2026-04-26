package com.community.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParkingManagementApplication.class, args);
        System.out.println("社区车辆违停处理系统启动成功：默认后端 http://localhost:8040，H2 控制台 /h2-console，MySQL 请使用 --spring.profiles.active=mysql");
    }
}
