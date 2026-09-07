package com.milk.config;

import com.milk.entity.User;
import com.milk.mapper.UserMapper;
import com.milk.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        if (isPublicGet(request)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            writeJson(response, 401, "未登录");
            return false;
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            String userId = JwtUtils.getUserIdFromToken(token);
            String role = JwtUtils.getRoleFromToken(token);
            Long id = Long.parseLong(userId);
            User user = userMapper.selectById(id);
            if (user == null || user.getStatus() == null || user.getStatus() != 1) {
                writeJson(response, 401, "用户不存在或已禁用");
                return false;
            }
            if (!hasPermission(request, role)) {
                writeJson(response, 403, "无权限访问");
                return false;
            }
            request.setAttribute("userId", id);
            request.setAttribute("role", role);
            return true;
        } catch (Exception e) {
            writeJson(response, 401, "token无效");
            return false;
        }
    }

    private boolean isPublicGet(HttpServletRequest request) {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String path = request.getRequestURI();
        return path.equals("/api/category/list")
                || path.equals("/api/product/list")
                || path.matches("/api/product/\\d+");
    }

    private boolean hasPermission(HttpServletRequest request, String role) {
        String path = request.getRequestURI();
        if ("ADMIN".equals(role)) {
            return true;
        }
        if (path.startsWith("/api/user")
                || path.startsWith("/api/category/page")
                || (path.startsWith("/api/category") && !"GET".equalsIgnoreCase(request.getMethod()))
                || path.startsWith("/api/product/page")
                || (path.startsWith("/api/product") && !"GET".equalsIgnoreCase(request.getMethod()))
                || path.startsWith("/api/area")
                || path.startsWith("/api/route")
                || path.startsWith("/api/stats")
                || path.startsWith("/api/subscription/page")
                || path.startsWith("/api/order/page")
                || path.startsWith("/api/complaint/page")
                || path.startsWith("/api/complaint/reply")) {
            return false;
        }
        if ("DELIVERY".equals(role)) {
            return path.startsWith("/api/delivery") || path.startsWith("/api/auth");
        }
        return "USER".equals(role) && !path.startsWith("/api/delivery");
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
