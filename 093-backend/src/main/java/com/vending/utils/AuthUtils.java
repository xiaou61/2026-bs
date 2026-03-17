package com.vending.utils;

import com.vending.common.BusinessException;

public class AuthUtils {

    public static void requireAdmin(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException(403, "无权限");
        }
    }
}
