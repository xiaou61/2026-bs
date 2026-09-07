package com.xiaou.campusclub.config;

import com.xiaou.campusclub.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final List<String> PUBLIC_GET_PATTERNS = List.of(
            "^/api/clubs$",
            "^/api/clubs/\\d+$",
            "^/api/activities$",
            "^/api/activities/\\d+$",
            "^/api/topics$",
            "^/api/topics/\\d+$",
            "^/api/topics/\\d+/comments$",
            "^/api/circles$",
            "^/api/circles/\\d+$",
            "^/api/circles/recommend$",
            "^/api/interests$",
            "^/api/interests/categories$"
    );

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (!hasUsableToken(token)) {
            if (isPublicGetRequest(request)) {
                return true;
            }
            writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "未登录或登录已过期");
            return false;
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            if (jwtUtil.isTokenExpired(token)) {
                writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "登录已过期");
                return false;
            }

            Long userId = jwtUtil.getUserId(token);
            String userType = jwtUtil.getType(token);
            request.setAttribute("userId", userId);
            request.setAttribute("username", jwtUtil.getUsername(token));
            request.setAttribute("userType", userType);

            if (request.getRequestURI().startsWith("/api/admin") && !"admin".equals(userType)) {
                writeError(response, HttpServletResponse.SC_FORBIDDEN, "无权限访问管理接口");
                return false;
            }

            return true;
        } catch (Exception e) {
            if (isPublicGetRequest(request)) {
                return true;
            }
            writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "登录凭证无效");
            return false;
        }
    }

    private boolean hasUsableToken(String token) {
        if (token == null) {
            return false;
        }
        String normalized = token.trim();
        return !normalized.isEmpty()
                && !"Bearer null".equalsIgnoreCase(normalized)
                && !"Bearer undefined".equalsIgnoreCase(normalized)
                && !"null".equalsIgnoreCase(normalized)
                && !"undefined".equalsIgnoreCase(normalized);
    }

    private boolean isPublicGetRequest(HttpServletRequest request) {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String uri = request.getRequestURI();
        return PUBLIC_GET_PATTERNS.stream().anyMatch(uri::matches);
    }

    private void writeError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}

