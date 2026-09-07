package com.harbin.tourism.utils;

import com.harbin.tourism.common.BusinessException;

import javax.servlet.http.HttpServletRequest;

public final class AuthUtils {

    private AuthUtils() {
    }

    public static Long currentUserId(HttpServletRequest request) {
        Object userId = request.getAttribute("userId");
        if (userId instanceof Long) {
            return (Long) userId;
        }
        throw new BusinessException(401, "未登录，请先登录");
    }

    public static String currentRole(HttpServletRequest request) {
        Object role = request.getAttribute("role");
        return role == null ? "" : role.toString();
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return "admin".equals(currentRole(request));
    }

    public static void requireAdmin(HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(403, "无权访问管理员资源");
        }
    }

    public static void requireOwnerOrAdmin(HttpServletRequest request, Long ownerId) {
        if (!isAdmin(request) && !currentUserId(request).equals(ownerId)) {
            throw new BusinessException(403, "无权访问该资源");
        }
    }
}
