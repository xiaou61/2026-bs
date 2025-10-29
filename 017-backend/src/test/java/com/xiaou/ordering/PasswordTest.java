package com.xiaou.ordering;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void testPasswordEncode() {
        String rawPassword = "123456";
        String encodedPassword = BCrypt.hashpw(rawPassword);

        System.out.println("原始密码: " + rawPassword);
        System.out.println("加密密码: " + encodedPassword);
        System.out.println("密码验证: " + BCrypt.checkpw(rawPassword, encodedPassword));

        String dbPassword = "$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O";
        System.out.println("\n数据库密码验证: " + BCrypt.checkpw(rawPassword, dbPassword));
        
        String adminPassword = "admin123";
        System.out.println("\n管理员密码: " + adminPassword);
        System.out.println("管理员密码验证: " + BCrypt.checkpw(adminPassword, dbPassword));
    }
}

