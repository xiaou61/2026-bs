package com.ticket.utils;

import com.ticket.common.BusinessException;

public class AuthUtils {

    public static void requireAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException(403, "无权限");
        }
    }
}
