package com.xiaou.sport;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void testPasswordEncrypt() {
        String password1 = "admin123";
        String password2 = "coach123";
        String password3 = "student123";

        String hash1 = BCrypt.hashpw(password1);
        String hash2 = BCrypt.hashpw(password2);
        String hash3 = BCrypt.hashpw(password3);

        System.out.println("admin password hash: " + hash1);
        System.out.println("coach password hash: " + hash2);
        System.out.println("student password hash: " + hash3);

        System.out.println("\nVerify admin: " + BCrypt.checkpw("admin123", hash1));
        System.out.println("Verify coach: " + BCrypt.checkpw("coach123", hash2));
        System.out.println("Verify student: " + BCrypt.checkpw("student123", hash3));
    }
}
