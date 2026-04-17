package com.xiaou.resource;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void testPasswordEncryption() {
        String originalPassword = "123456";
        String encryptedPassword = DigestUtil.md5Hex(originalPassword);

        Assertions.assertEquals("e10adc3949ba59abbe56e057f20f883e", encryptedPassword);
    }

    @Test
    public void testMultiplePasswords() {
        String[] passwords = {"123456", "admin", "password"};

        for (String password : passwords) {
            String encrypted = DigestUtil.md5Hex(password);
            Assertions.assertNotNull(encrypted);
            Assertions.assertEquals(32, encrypted.length());
        }
    }
}

