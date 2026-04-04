package com.xiaou.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.common.Result;
import com.xiaou.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Set<String> ADMIN_ROLES = Set.of("ADMIN", "STATION_ADMIN");
    private static final Set<String> OPERATOR_ROLES = Set.of("ADMIN", "STATION_ADMIN", "COURIER");
    private static final Pattern USER_PASSWORD_PATH = Pattern.compile("^/api/user/(\\d+)/password$");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            Result<?> result = Result.error(401, "未登录或token已过期");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
            return false;
        }

        try {
            JwtUtil.verify(token);
            String role = JwtUtil.getRole(token);
            Long userId = JwtUtil.getUserId(token);
            String path = request.getRequestURI();

            if (!hasPermission(path, role, userId)) {
                writeJson(response, 403, "无权访问该接口");
                return false;
            }
            return true;
        } catch (JWTVerificationException e) {
            writeJson(response, 401, "token无效或已过期");
            return false;
        }
    }

    private boolean hasPermission(String path, String role, Long userId) {
        if ("/api/auth/info".equals(path)) {
            return true;
        }

        if (path.startsWith("/api/notification/")) {
            return true;
        }

        if ("/api/overdue/my".equals(path)) {
            return true;
        }

        if ("/api/express/my-packages".equals(path) || "/api/express/my-history".equals(path)) {
            return true;
        }

        Matcher matcher = USER_PASSWORD_PATH.matcher(path);
        if (matcher.matches()) {
            Long pathUserId = Long.parseLong(matcher.group(1));
            return ADMIN_ROLES.contains(role) || pathUserId.equals(userId);
        }

        if (path.startsWith("/api/user/")
                || path.startsWith("/api/stats/")
                || path.startsWith("/api/station/")
                || path.startsWith("/api/company/")
                || path.startsWith("/api/log/")
                || path.startsWith("/api/config/")) {
            return ADMIN_ROLES.contains(role);
        }

        if (path.startsWith("/api/overdue/")) {
            return ADMIN_ROLES.contains(role);
        }

        if (path.startsWith("/api/express/")) {
            return OPERATOR_ROLES.contains(role);
        }

        return true;
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        Result<?> result = Result.error(status, message);
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}

