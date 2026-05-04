package com.petcafe.utils;

import com.petcafe.common.BusinessException;

public class AuthUtils {

    public static void requireAdmin(String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new BusinessException(403, "无权限");
        }
    }

    public static void requireManager(String role) {
        if (!"ADMIN".equalsIgnoreCase(role) && !"STAFF".equalsIgnoreCase(role)) {
            throw new BusinessException(403, "无权限");
        }
    }

    public static void requireCustomer(String role) {
        if (!"CUSTOMER".equalsIgnoreCase(role)) {
            throw new BusinessException(403, "无权限");
        }
    }
}
