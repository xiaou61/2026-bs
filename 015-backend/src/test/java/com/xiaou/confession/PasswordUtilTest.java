package com.xiaou.confession;

import com.xiaou.confession.util.PasswordUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {
    
    @Test
    public void testPasswordEncryption() {
        String rawPassword1 = "admin123";
        String rawPassword2 = "123456";
        
        String encoded1 = PasswordUtil.encode(rawPassword1);
        String encoded2 = PasswordUtil.encode(rawPassword2);
        
        System.out.println("admin123 加密后: " + encoded1);
        System.out.println("123456 加密后: " + encoded2);
        
        assertTrue(PasswordUtil.matches(rawPassword1, encoded1));
        assertTrue(PasswordUtil.matches(rawPassword2, encoded2));
        
        assertFalse(PasswordUtil.matches("wrongpassword", encoded1));
        assertFalse(PasswordUtil.matches("wrongpassword", encoded2));
        
        System.out.println("✅ 密码加密测试通过！");
    }
    
    @Test
    public void testDatabasePassword() {
        String dbPassword = "$2a$10$xQh7YKZjYvFjXXLLQpWqUeN9Z3jZvN7lXNT8Y3kJqN.wJqGqVHRQW";
        
        boolean matchAdmin = PasswordUtil.matches("admin123", dbPassword);
        boolean matchUser = PasswordUtil.matches("123456", dbPassword);
        
        System.out.println("数据库密码验证 admin123: " + matchAdmin);
        System.out.println("数据库密码验证 123456: " + matchUser);
        
        assertTrue(matchAdmin || matchUser);
        
        System.out.println("✅ 数据库密码验证通过！");
    }
}

