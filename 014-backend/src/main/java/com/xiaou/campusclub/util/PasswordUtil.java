package com.xiaou.campusclub.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}

