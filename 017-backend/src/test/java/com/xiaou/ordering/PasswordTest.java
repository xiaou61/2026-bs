package com.xiaou.ordering;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordTest {

    private static final String USER_PASSWORD_HASH = "$2a$10$sdFcezRDFQYxIX8qs7vW1el.gIGgP7eBv79p937wzsk5YHbPpAkYO";
    private static final String ADMIN_PASSWORD_HASH = "$2a$10$rYMe2maxvcYuP3FonEsxKOS5wBaEugDlwMR16JalPU3rAmfhDEGsi";

    @Test
    public void testSeedPasswords() {
        assertTrue(BCrypt.checkpw("123456", USER_PASSWORD_HASH), "默认学生/商家密码哈希应匹配 123456");
        assertTrue(BCrypt.checkpw("admin123", ADMIN_PASSWORD_HASH), "默认管理员密码哈希应匹配 admin123");
        assertFalse(BCrypt.checkpw("admin123", USER_PASSWORD_HASH), "学生/商家密码哈希不应匹配管理员密码");
        assertFalse(BCrypt.checkpw("123456", ADMIN_PASSWORD_HASH), "管理员密码哈希不应匹配学生密码");
    }
}

