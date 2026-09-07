package com.mfg.config;

import cn.hutool.core.util.StrUtil;
import com.mfg.entity.User;
import com.mfg.mapper.UserMapper;
import com.mfg.utils.JwtUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;
    private final UserMapper userMapper;

    public JwtInterceptor(StringRedisTemplate redisTemplate, UserMapper userMapper) {
        this.redisTemplate = redisTemplate;
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
            String redisToken = getCachedToken(userId);
            if (redisToken != null && !redisToken.equals(token)) {
                writeJson(response, 401, "token已过期");
                return false;
            }
            if (!hasPermission(request, user.getRole())) {
                writeJson(response, 403, "无权限访问");
                return false;
            }
            request.setAttribute("userId", userId);
            request.setAttribute("role", user.getRole());
            return true;
        } catch (Exception e) {
            writeJson(response, 401, "token无效");
            return false;
        }
    }

    private String getCachedToken(String userId) {
        try {
            return redisTemplate.opsForValue().get("token:" + userId);
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    private boolean hasPermission(HttpServletRequest request, String role) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        if ("admin".equals(role)) {
            return true;
        }
        if (path.equals("/api/user/info") || path.equals("/api/logout")) {
            return true;
        }
        if (path.startsWith("/api/user")) {
            return false;
        }
        if (path.startsWith("/api/dashboard")) {
            return true;
        }
        if ("manager".equals(role)) {
            return true;
        }
        if ("operator".equals(role)) {
            return path.startsWith("/api/equipment")
                    || path.startsWith("/api/equipmentCategory")
                    || path.startsWith("/api/sensorData")
                    || path.startsWith("/api/alert")
                    || path.startsWith("/api/maintenance")
                    || path.startsWith("/api/product/list")
                    || path.startsWith("/api/productionOrder/page");
        }
        if ("inspector".equals(role)) {
            return path.startsWith("/api/qualityInspection")
                    || path.startsWith("/api/product/list")
                    || path.startsWith("/api/productionOrder/page")
                    || ("GET".equalsIgnoreCase(method) && path.startsWith("/api/equipment"));
        }
        return false;
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
