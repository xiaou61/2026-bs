package com.xiaou.herbal.util;

public class UserContext {

    private static final ThreadLocal<Long> userIdContext = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        userIdContext.set(userId);
    }

    public static Long getUserId() {
        return userIdContext.get();
    }

    public static void clear() {
        userIdContext.remove();
    }
}
