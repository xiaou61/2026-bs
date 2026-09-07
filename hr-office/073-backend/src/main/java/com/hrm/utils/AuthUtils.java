package com.hrm.utils;

import com.hrm.common.BusinessException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public final class AuthUtils {

    private AuthUtils() {
    }

    public static Long currentUserId(HttpServletRequest request) {
        Object userId = request.getAttribute("userId");
        if (userId instanceof Long) {
            return (Long) userId;
        }
        throw new BusinessException(401, "未登录");
    }

    public static String currentRole(HttpServletRequest request) {
        Object role = request.getAttribute("role");
        return role == null ? "" : role.toString();
    }

    public static boolean hasAnyRole(HttpServletRequest request, String... roles) {
        String role = currentRole(request);
        return Arrays.asList(roles).contains(role);
    }

    public static boolean isAdminOrHr(HttpServletRequest request) {
        return hasAnyRole(request, "admin", "hr");
    }

    public static void requireAdminOrHr(HttpServletRequest request) {
        if (!isAdminOrHr(request)) {
            throw new BusinessException(403, "无权访问人事管理资源");
        }
    }

    public static void requireAdmin(HttpServletRequest request) {
        if (!hasAnyRole(request, "admin")) {
            throw new BusinessException(403, "无权访问管理员资源");
        }
    }
}
