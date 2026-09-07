package com.folksong.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FolksongPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(FolksongPlatformApplication.class, args);
        System.out.println("民歌民谣交流平台启动成功：默认后端 http://localhost:8039，H2 控制台 /h2-console，PostgreSQL 请使用 --spring.profiles.active=postgresql");
    }
}
