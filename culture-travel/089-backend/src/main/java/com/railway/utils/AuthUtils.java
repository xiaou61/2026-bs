package com.railway.utils;

import com.railway.common.BusinessException;

public class AuthUtils {

    public static void requireAdmin(String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new BusinessException(403, "无权限");
        }
    }

    public static void requireStaff(String role) {
        if (!"ADMIN".equalsIgnoreCase(role) && !"DISPATCHER".equalsIgnoreCase(role)) {
            throw new BusinessException(403, "无权限");
        }
    }
}
