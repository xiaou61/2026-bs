package com.xiaou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园事务管理系统启动类
 * @author xiaou
 */
@SpringBootApplication
@MapperScan("com.xiaou.mapper")
public class CampusAffairsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CampusAffairsApplication.class, args);
        System.out.println("====================================");
        System.out.println("校园事务管理系统启动成功!");
        System.out.println("====================================");
    }
}

