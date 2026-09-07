package com.teachres.utils;

import com.teachres.common.BusinessException;

import java.util.Arrays;

public final class AuthUtils {

    private AuthUtils() {
    }

    public static boolean hasRole(String role, String... allowedRoles) {
        return Arrays.asList(allowedRoles).contains(role);
    }

    public static void requireRole(String role, String... allowedRoles) {
        if (!hasRole(role, allowedRoles)) {
            throw new BusinessException(403, "无权访问");
        }
    }

    public static void requireAdmin(String role) {
        requireRole(role, "admin");
    }

    public static void requireOwnerOrAdmin(Long ownerId, Long userId, String role) {
        if (!"admin".equals(role) && (ownerId == null || !ownerId.equals(userId))) {
            throw new BusinessException(403, "无权访问");
        }
    }
}
