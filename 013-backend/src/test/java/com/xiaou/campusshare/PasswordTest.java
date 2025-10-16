package com.xiaou.campusshare;

import cn.hutool.crypto.digest.BCrypt;

public class PasswordTest {
    public static void main(String[] args) {
        String password = "123456";
        
        String hashed1 = BCrypt.hashpw(password);
        String hashed2 = BCrypt.hashpw(password);
        String hashed3 = BCrypt.hashpw(password);
        
        System.out.println("密码: " + password);
        System.out.println("BCrypt加密1: " + hashed1);
        System.out.println("BCrypt加密2: " + hashed2);
        System.out.println("BCrypt加密3: " + hashed3);
        
        System.out.println("\n验证测试:");
        System.out.println("使用hashed1验证: " + BCrypt.checkpw(password, hashed1));
        System.out.println("使用hashed2验证: " + BCrypt.checkpw(password, hashed2));
        System.out.println("使用hashed3验证: " + BCrypt.checkpw(password, hashed3));
        
        System.out.println("\n建议使用的密码哈希值（复制到SQL中）:");
        System.out.println(hashed1);
    }
}

