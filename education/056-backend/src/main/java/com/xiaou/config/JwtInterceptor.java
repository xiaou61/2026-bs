package com.xiaou.config;

import com.xiaou.entity.User;
import com.xiaou.mapper.UserMapper;
import com.xiaou.utils.JwtUtils;
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
    private UserMapper userMapper;

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
        if (jwtUtils.isTokenExpired(token)) {
            writeJson(response, 401, "登录已过期");
            return false;
        }
        Long userId = jwtUtils.getUserId(token);
        Integer role = jwtUtils.getRole(token);
        User user = userMapper.selectById(userId);
        if (user == null || user.getStatus() == null || user.getStatus() != 1) {
            writeJson(response, 401, "用户不存在或已禁用");
            return false;
        }
        request.setAttribute("userId", userId);
        request.setAttribute("role", role);
        if (isAdminOnly(request) && role != 0) {
            writeJson(response, 403, "无权限访问");
            return false;
        }
        if (isJudgeOnly(request) && role > 1) {
            writeJson(response, 403, "无权限访问");
            return false;
        }
        return true;
    }

    private boolean isAdminOnly(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        if (path.startsWith("/api/user")
                && !path.equals("/api/user/info")
                && !path.equals("/api/user/changePassword")
                && !path.equals("/api/user/updateInfo")) {
            return true;
        }
        if (path.startsWith("/api/stats")) {
            return true;
        }
        if (path.startsWith("/api/category") && !("GET".equalsIgnoreCase(method) && path.equals("/api/category/all"))) {
            return true;
        }
        if (path.startsWith("/api/competition")
                && !path.startsWith("/api/competition/public")
                && !("GET".equalsIgnoreCase(method) && path.matches("/api/competition/\\d+"))) {
            return true;
        }
        if (path.startsWith("/api/notice") && !path.startsWith("/api/notice/public")) {
            return true;
        }
        if (path.startsWith("/api/judge") || path.startsWith("/api/score-standard")) {
            return true;
        }
        if (path.startsWith("/api/work/list") || path.startsWith("/api/work/audit")) {
            return true;
        }
        return path.startsWith("/api/award/generate");
    }

    private boolean isJudgeOnly(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/score/pending") || path.startsWith("/api/score/submit") || path.startsWith("/api/score/my");
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
