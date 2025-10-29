package com.xiaou.recruitment;

import com.xiaou.recruitment.utils.PasswordUtil;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void testPasswordEncrypt() {
        String password = "123456";
        String encrypted = PasswordUtil.encrypt(password);
        System.out.println("原始密码: " + password);
        System.out.println("加密后: " + encrypted);
        System.out.println("验证结果: " + PasswordUtil.verify(password, encrypted));
    }

    @Test
    public void generateTestPasswords() {
        String[] passwords = { "123456", "admin123", "company123" };
        for (String password : passwords) {
            System.out.println("密码: " + password + " -> 加密后: " + PasswordUtil.encrypt(password));
        }
    }
}
