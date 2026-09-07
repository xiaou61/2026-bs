package com.football.utils;

import com.football.common.BusinessException;

public class AuthUtils {

    public static void requireAdmin(String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new BusinessException(403, "无权限");
        }
    }

    public static void requireManager(String role) {
        if (!"ADMIN".equalsIgnoreCase(role) && !"MANAGER".equalsIgnoreCase(role)) {
            throw new BusinessException(403, "无权限");
        }
    }
}
