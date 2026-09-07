package com.eldercare.utils;

import com.eldercare.common.BusinessException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
        if (value != null) {
            return Long.parseLong(String.valueOf(value));
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

    public static boolean isDoctor(HttpServletRequest request) {
        return "doctor".equalsIgnoreCase(getRole(request));
    }

    public static void requireAdmin(HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(403, "无权访问");
        }
    }

    public static void requireAnyRole(HttpServletRequest request, String... roles) {
        String role = getRole(request);
        if (role == null || Arrays.stream(roles).noneMatch(item -> item.equalsIgnoreCase(role))) {
            throw new BusinessException(403, "无权访问");
        }
    }

    public static void requireOwnerOrAdmin(HttpServletRequest request, Long ownerId) {
        Long userId = getUserId(request);
        if (ownerId == null) {
            throw new BusinessException(403, "无权操作");
        }
        if (!userId.equals(ownerId) && !isAdmin(request)) {
            throw new BusinessException(403, "无权操作");
        }
    }
}
