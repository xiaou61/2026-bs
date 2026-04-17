package com.xiaou.bike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 校园共享自行车租赁系统启动类
 */
@SpringBootApplication
@MapperScan("com.xiaou.bike.mapper")
public class CampusBikeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CampusBikeApplication.class, args);
        String port = context.getEnvironment().getProperty("local.server.port",
                context.getEnvironment().getProperty("server.port", "8028"));
        System.out.println("==================================================");
        System.out.println("    校园共享自行车租赁系统启动成功!");
        System.out.println("    接口文档: http://localhost:" + port + "/doc.html");
        System.out.println("==================================================");
    }
}
