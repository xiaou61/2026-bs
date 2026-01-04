package com.xiaou.dreamdonation.util;

import cn.hutool.crypto.digest.BCrypt;

public class PasswordUtil {
    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
