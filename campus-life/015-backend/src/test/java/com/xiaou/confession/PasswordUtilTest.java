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
        String adminPassword = "$2a$10$riBv.FgkCsPlAykJIufhhuheh3SepA3okxLskb4OwSBHpymavCi3G";
        String userPassword = "$2a$10$KjQ0pB8gAqIgmfMGzgTc1u3V3UAFy2.7QmWo5Y3eVuuuPVsKPm6wi";

        boolean matchAdmin = PasswordUtil.matches("admin123", adminPassword);
        boolean matchUser = PasswordUtil.matches("123456", userPassword);

        System.out.println("数据库管理员密码验证 admin123: " + matchAdmin);
        System.out.println("数据库用户密码验证 123456: " + matchUser);

        assertTrue(matchAdmin);
        assertTrue(matchUser);
        
        System.out.println("✅ 数据库密码验证通过！");
    }
}

