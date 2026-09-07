package com.xiaou.express;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordTest {

    @Test
    public void shouldMatchSeedUserPassword() {
        String userPasswordHash = "$2a$10$GALOcWmXAWolABT6SJi6E.B3uVPyTuxKhb3mnyzZQWhxQ5NSaQ5le";
        assertTrue(BCrypt.checkpw("123456", userPasswordHash));
    }

    @Test
    public void shouldMatchSeedAdminPassword() {
        String adminPasswordHash = "$2a$10$dj2f82N4tHWrjvLvM0yhUuxu016fiiKSgjofdTJNcA6V5KnRivyMO";
        assertTrue(BCrypt.checkpw("admin123", adminPasswordHash));
    }
}
