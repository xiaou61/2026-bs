package com.enrollment.config;

import com.enrollment.entity.Admin;
import com.enrollment.mapper.AdminMapper;
import com.enrollment.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
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
        if (!jwtUtils.validateToken(token)) {
            writeJson(response, 401, "token无效");
            return false;
        }
        String userId = jwtUtils.getUserIdFromToken(token);
        Admin admin = adminMapper.selectById(Long.parseLong(userId));
        if (admin == null || admin.getStatus() == null || admin.getStatus() != 1) {
            writeJson(response, 401, "用户不存在或已禁用");
            return false;
        }
        if (request.getRequestURI().startsWith("/api/admin") && !"super_admin".equals(admin.getRole())) {
            writeJson(response, 403, "无权限访问");
            return false;
        }
        request.setAttribute("userId", userId);
        request.setAttribute("role", admin.getRole());
        return true;
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
