package com.movie.config;

import cn.hutool.core.util.StrUtil;
import com.movie.entity.User;
import com.movie.mapper.UserMapper;
import com.movie.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    private final UserMapper userMapper;

    public JwtInterceptor(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StrUtil.isBlank(token)) {
            writeJson(response, 401, "未登录");
            return false;
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            String userId = JwtUtils.getUserIdFromToken(token);
            User user = userMapper.selectById(Long.valueOf(userId));
            if (user == null || user.getStatus() == null || user.getStatus() != 1) {
                writeJson(response, 401, "用户不存在或已禁用");
                return false;
            }
            if (!hasPermission(request, user.getRole())) {
                writeJson(response, 403, "无权限访问");
                return false;
            }
            request.setAttribute("userId", user.getId());
            request.setAttribute("role", user.getRole());
            return true;
        } catch (Exception e) {
            writeJson(response, 401, "token无效");
            return false;
        }
    }

    private boolean hasPermission(HttpServletRequest request, String role) {
        if ("admin".equals(role)) {
            return true;
        }
        String path = request.getRequestURI();
        String method = request.getMethod();
        if (path.startsWith("/api/auth")) {
            return true;
        }
        if (path.startsWith("/api/user") || path.startsWith("/api/dashboard")) {
            return false;
        }
        if (path.startsWith("/api/order")) {
            return path.equals("/api/order/my")
                    || path.startsWith("/api/order/pay/")
                    || path.startsWith("/api/order/cancel/")
                    || ("POST".equalsIgnoreCase(method) && path.equals("/api/order"));
        }
        if (path.startsWith("/api/favorite")) {
            return true;
        }
        if (path.startsWith("/api/review")) {
            return path.startsWith("/api/review/movie/")
                    || ("POST".equalsIgnoreCase(method) && path.equals("/api/review"));
        }
        if (path.startsWith("/api/movie")
                || path.startsWith("/api/movieCategory")
                || path.startsWith("/api/cinema")
                || path.startsWith("/api/hall")
                || path.startsWith("/api/showtime")
                || path.startsWith("/api/announcement")) {
            return "GET".equalsIgnoreCase(method);
        }
        return false;
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
