package com.xiaou.ordering.util;

public class UserContext {
    private static final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> userTypeThreadLocal = new ThreadLocal<>();

    public static void setCurrentUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }

    public static Long getCurrentUserId() {
        return userIdThreadLocal.get();
    }

    public static void setCurrentUserType(String userType) {
        userTypeThreadLocal.set(userType);
    }

    public static String getCurrentUserType() {
        return userTypeThreadLocal.get();
    }

    public static void clear() {
        userIdThreadLocal.remove();
        userTypeThreadLocal.remove();
    }
}

