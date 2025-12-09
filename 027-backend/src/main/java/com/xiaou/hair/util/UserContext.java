package com.xiaou.hair.util;

/**
 * 用户上下文工具类（ThreadLocal存储当前用户信息）
 */
public class UserContext {

    private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();

    /**
     * 设置当前用户ID
     */
    public static void setUserId(Long userId) {
        userIdHolder.set(userId);
    }

    /**
     * 获取当前用户ID
     */
    public static Long getUserId() {
        return userIdHolder.get();
    }

    /**
     * 清除当前用户信息
     */
    public static void clear() {
        userIdHolder.remove();
    }
}
