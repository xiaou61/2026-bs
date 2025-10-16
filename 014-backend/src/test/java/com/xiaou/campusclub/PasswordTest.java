package com.xiaou.campusclub;

import com.xiaou.campusclub.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordTest {
    
    @Test
    public void testPasswordEncode() {
        String rawPassword = "123456";
        String encodedPassword = PasswordUtil.encode(rawPassword);
        System.out.println("原始密码: " + rawPassword);
        System.out.println("加密后密码: " + encodedPassword);
        
        boolean matches = PasswordUtil.matches(rawPassword, encodedPassword);
        System.out.println("密码验证结果: " + matches);
        
        assert matches : "密码验证失败";
    }
    
    @Test
    public void testPasswordMatches() {
        String rawPassword = "123456";
        String encodedPassword = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi";
        
        boolean matches = PasswordUtil.matches(rawPassword, encodedPassword);
        System.out.println("测试密码: " + rawPassword);
        System.out.println("数据库密码: " + encodedPassword);
        System.out.println("验证结果: " + matches);
        
        assert matches : "密码验证失败";
    }
    
    @Test
    public void generatePasswords() {
        System.out.println("=== 生成测试账户密码 ===");
        String[] passwords = {"123456", "admin123", "user123"};
        
        for (String password : passwords) {
            String encoded = PasswordUtil.encode(password);
            System.out.println("原始密码: " + password + " -> 加密密码: " + encoded);
        }
    }
}

