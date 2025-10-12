package com.xiaou.utils;

import java.security.MessageDigest;

public class MD5Util {
    
    public static String encrypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            StringBuilder buf = new StringBuilder();
            for (byte b : byteDigest) {
                buf.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return buf.toString();
        } catch (Exception e) {
            return null;
        }
    }
}

