package com.groupbuy.utils;

import com.groupbuy.common.BusinessException;

import javax.servlet.http.HttpServletRequest;

public final class AuthUtils {

    private AuthUtils() {
    }

    public static Long getUserId(HttpServletRequest request) {
        Object value = request.getAttribute("userId");
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value != null) {
            return Long.valueOf(value.toString());
        }
        throw new BusinessException(401, "未登录");
    }

    public static Integer getRole(HttpServletRequest request) {
        Object value = request.getAttribute("role");
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value != null) {
            return Integer.valueOf(value.toString());
        }
        throw new BusinessException(401, "未登录");
    }

    public static void requireRole(HttpServletRequest request, int... allowedRoles) {
        Integer role = getRole(request);
        for (int allowedRole : allowedRoles) {
            if (role == allowedRole) {
                return;
            }
        }
        throw new BusinessException(403, "无权限");
    }

    public static void requireAdmin(HttpServletRequest request) {
        requireRole(request, 0);
    }

    public static void requireMerchant(HttpServletRequest request) {
        requireRole(request, 1);
    }

    public static void requireUser(HttpServletRequest request) {
        requireRole(request, 2);
    }
}
