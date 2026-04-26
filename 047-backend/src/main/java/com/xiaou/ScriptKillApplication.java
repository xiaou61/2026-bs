package com.xiaou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.mapper")
public class ScriptKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScriptKillApplication.class, args);
        System.out.println("剧本杀创作与预约平台启动成功！");
        System.out.println("接口文档：http://localhost:8047/doc.html");
        System.out.println("H2控制台：http://localhost:8047/h2-console");
    }
}
