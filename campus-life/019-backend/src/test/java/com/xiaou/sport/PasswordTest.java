package com.xiaou.sport;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void shouldVerifyGeneratedHashes() {
        String password = "admin123";
        String hash = BCrypt.hashpw(password);

        Assertions.assertNotEquals(password, hash);
        Assertions.assertTrue(BCrypt.checkpw(password, hash));
        Assertions.assertFalse(BCrypt.checkpw("wrong-password", hash));
    }

    @Test
    public void shouldMatchDocumentedDefaultAccounts() {
        Assertions.assertTrue(BCrypt.checkpw("admin123",
                "$2a$10$2fPFYCZcP0yOZRIqBS.JYeJKTo2ylU32nay4WcYNZt9k0phajCKEC"));
        Assertions.assertTrue(BCrypt.checkpw("coach123",
                "$2a$10$Pf2Qj.4cTb6taQ3UKUS3neMp2k5BjyerAPefRZPwFDrz5VGZT6WQa"));
        Assertions.assertTrue(BCrypt.checkpw("student123",
                "$2a$10$R7SC20YJ69IB0RxumoirqO4tF1YyjOGwQ2L5SBwMpUy/Vve3ZttmS"));
    }
}
