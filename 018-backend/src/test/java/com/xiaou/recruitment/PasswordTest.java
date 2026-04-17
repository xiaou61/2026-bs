package com.xiaou.recruitment;

import com.xiaou.recruitment.utils.PasswordUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordTest {

    @Test
    public void testPasswordEncrypt() {
        String password = "123456";
        String encrypted = PasswordUtil.encrypt(password);
        assertEquals("e10adc3949ba59abbe56e057f20f883e", encrypted);
        assertTrue(PasswordUtil.verify(password, encrypted));
    }

    @Test
    public void generateTestPasswords() {
        assertEquals("0192023a7bbd73250516f069df18b500", PasswordUtil.encrypt("admin123"));
        assertEquals("f13ba28db45b86089ace6be57551b769", PasswordUtil.encrypt("company123"));
    }
}
