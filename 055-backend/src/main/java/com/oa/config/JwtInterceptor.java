package com.oa.config;

import com.oa.entity.User;
import com.oa.mapper.UserMapper;
import com.oa.utils.JwtUtils;
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
    private UserMapper userMapper;

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
            writeJson(response, 401, "token无效或已过期");
            return false;
        }
        Long userId = jwtUtils.getUserId(token);
        User user = userMapper.selectById(userId);
        if (user == null || user.getStatus() == null || user.getStatus() != 1) {
            writeJson(response, 401, "用户不存在或已禁用");
            return false;
        }

        request.setAttribute("userId", userId);
        request.setAttribute("role", user.getRole());
        if (isAdminOnly(request) && !"admin".equalsIgnoreCase(user.getRole())) {
            writeJson(response, 403, "无权限访问");
            return false;
        }
        return true;
    }

    private boolean isAdminOnly(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        if (path.startsWith("/api/user") && !path.equals("/api/user/info")
                && !path.equals("/api/user/password") && !path.equals("/api/user/profile")
                && !path.equals("/api/user/login")) {
            return true;
        }
        if (path.startsWith("/api/department")) {
            return true;
        }
        if (path.startsWith("/api/meetingRoom") || path.startsWith("/api/meeting-room")) {
            return true;
        }
        return path.startsWith("/api/salary") && !"GET".equalsIgnoreCase(method);
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
