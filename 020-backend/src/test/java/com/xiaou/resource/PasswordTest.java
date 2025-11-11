package com.xiaou.resource;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void testPasswordEncryption() {
        String originalPassword = "123456";
        String encryptedPassword = DigestUtil.md5Hex(originalPassword);
        
        System.out.println("原始密码: " + originalPassword);
        System.out.println("MD5加密后: " + encryptedPassword);
        System.out.println("预期结果: e10adc3949ba59abbe56e057f20f883e");
        System.out.println("加密是否正确: " + encryptedPassword.equals("e10adc3949ba59abbe56e057f20f883e"));
        
        assert encryptedPassword.equals("e10adc3949ba59abbe56e057f20f883e") : "密码加密不正确";
    }

    @Test
    public void testMultiplePasswords() {
        String[] passwords = {"123456", "admin", "password"};
        
        for (String password : passwords) {
            String encrypted = DigestUtil.md5Hex(password);
            System.out.println("密码: " + password + " => MD5: " + encrypted);
        }
    }
}

