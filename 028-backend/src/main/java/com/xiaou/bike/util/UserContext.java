package com.xiaou.bike.util;

/**
 * 用户上下文工具类
 * 用于在线程中存储和获取当前用户信息
 */
public class UserContext {

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USERNAME = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_TYPE = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static void setUsername(String username) {
        USERNAME.set(username);
    }

    public static String getUsername() {
        return USERNAME.get();
    }

    public static void setUserType(String userType) {
        USER_TYPE.set(userType);
    }

    public static String getUserType() {
        return USER_TYPE.get();
    }

    /**
     * 判断是否为管理员
     */
    public static boolean isAdmin() {
        return "admin".equals(USER_TYPE.get());
    }

    /**
     * 判断是否为普通用户
     */
    public static boolean isUser() {
        return "user".equals(USER_TYPE.get());
    }

    /**
     * 清除上下文信息
     */
    public static void clear() {
        USER_ID.remove();
        USERNAME.remove();
        USER_TYPE.remove();
    }
}
