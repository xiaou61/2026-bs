package com.xiaou.recruitment.utils;

import cn.hutool.crypto.digest.MD5;

public class PasswordUtil {

    public static String encrypt(String password) {
        return MD5.create().digestHex(password);
    }

    public static boolean verify(String rawPassword, String encryptedPassword) {
        return encrypt(rawPassword).equals(encryptedPassword);
    }
}
