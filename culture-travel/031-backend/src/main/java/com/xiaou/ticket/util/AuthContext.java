package com.xiaou.ticket.util;

public final class AuthContext {

    private static final ThreadLocal<AuthUser> CURRENT_USER = new ThreadLocal<>();

    private AuthContext() {
    }

    public static void set(AuthUser authUser) {
        CURRENT_USER.set(authUser);
    }

    public static AuthUser get() {
        return CURRENT_USER.get();
    }

    public static Long getUserId() {
        AuthUser authUser = CURRENT_USER.get();
        return authUser == null ? null : authUser.userId();
    }

    public static String getRole() {
        AuthUser authUser = CURRENT_USER.get();
        return authUser == null ? null : authUser.role();
    }

    public static boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(getRole());
    }

    public static void clear() {
        CURRENT_USER.remove();
    }

    public record AuthUser(Long userId, String username, String role) {
    }
}
