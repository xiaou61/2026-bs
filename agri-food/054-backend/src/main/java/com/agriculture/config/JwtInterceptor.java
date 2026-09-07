package com.agriculture.config;

import com.agriculture.utils.JwtUtils;
import com.agriculture.entity.User;
import com.agriculture.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || token.isEmpty()) {
            writeJson(response, 401, "未登录");
            return false;
        }

        if (!jwtUtils.validateToken(token)) {
            writeJson(response, 401, "token无效");
            return false;
        }

        String userId = jwtUtils.getUserIdFromToken(token);
        User user = userService.getById(Long.parseLong(userId));
        if (user == null || user.getStatus() == 0) {
            writeJson(response, 401, "用户不存在或已禁用");
            return false;
        }

        request.setAttribute("userId", userId);
        request.setAttribute("role", user.getRole());

        if (isAdminOnly(request) && !"ADMIN".equalsIgnoreCase(user.getRole())) {
            writeJson(response, 403, "无权限访问");
            return false;
        }

        return true;
    }

    private boolean isAdminOnly(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        if (path.startsWith("/api/stats")) {
            return true;
        }
        if (path.equals("/api/user/page")) {
            return true;
        }
        if (path.equals("/api/user") && !"GET".equalsIgnoreCase(method)) {
            return true;
        }
        if (path.equals("/api/notice/page")) {
            return true;
        }
        return path.equals("/api/notice") && !"GET".equalsIgnoreCase(method);
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
