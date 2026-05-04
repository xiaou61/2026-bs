package com.cinema.utils;

import com.cinema.common.BusinessException;

public class AuthUtils {

    public static void requireAdmin(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException(403, "无权限");
        }
    }

    public static void requireMember(String role) {
        if (!"MEMBER".equals(role)) {
            throw new BusinessException(403, "仅会员可操作");
        }
    }
}
