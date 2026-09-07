package com.alumni.utils;

import com.alumni.common.BusinessException;

import javax.servlet.http.HttpServletRequest;

public final class AuthUtils {

    private AuthUtils() {
    }

    public static Long getUserId(HttpServletRequest request) {
        Object value = request.getAttribute("userId");
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        throw new BusinessException(401, "请先登录");
    }

    public static String getRole(HttpServletRequest request) {
        Object value = request.getAttribute("role");
        return value == null ? null : String.valueOf(value);
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return "admin".equalsIgnoreCase(getRole(request));
    }

    public static void requireAdmin(HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(403, "无权访问");
        }
    }

    public static void requireOwnerOrAdmin(HttpServletRequest request, Long ownerId) {
        Long userId = getUserId(request);
        if (!userId.equals(ownerId) && !isAdmin(request)) {
            throw new BusinessException(403, "无权操作");
        }
    }
}
