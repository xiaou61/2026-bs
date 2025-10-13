package com.xiaou.campusvideo.util;

public class UserHolder {
    private static final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();
    
    public static void setUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }
    
    public static Long getUserId() {
        return userIdThreadLocal.get();
    }
    
    public static void removeUserId() {
        userIdThreadLocal.remove();
    }
}

