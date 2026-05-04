package com.bike.config;

import com.bike.common.BusinessException;
import com.bike.entity.User;
import com.bike.mapper.UserMapper;
import com.bike.service.RuntimeStoreService;
import com.bike.utils.JwtUtils;
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
    private RuntimeStoreService runtimeStoreService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String path = request.getRequestURI();
        String token = normalizeToken(request.getHeader("Authorization"));
        if (token == null || token.isEmpty()) {
            throw new BusinessException(401, "未登录");
        }
        try {
            if (jwtUtils.isTokenExpired(token)) {
                throw new BusinessException(401, "登录已过期");
            }
            String userId = jwtUtils.getUserIdFromToken(token);
            String role = jwtUtils.getRoleFromToken(token);
            Long currentUserId = Long.parseLong(userId);
            User user = userMapper.findById(currentUserId);
            if (user == null || user.getStatus() == null || user.getStatus() != 1) {
                throw new BusinessException(401, "账号不可用");
            }
            if (!runtimeStoreService.isTokenActive(currentUserId, token)) {
                throw new BusinessException(401, "登录状态失效");
            }
            checkPermission(path, role);
            request.setAttribute("userId", currentUserId);
            request.setAttribute("role", role);
            return true;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(401, "无效的token");
        }
    }

    private String normalizeToken(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        if (token.startsWith("Bearer ")) {
            return token.substring(7).trim();
        }
        return token.trim();
    }

    private void checkPermission(String path, String role) {
        if (requiresAdmin(path) && !isAdmin(role)) {
            throw new BusinessException(403, "无权限");
        }
        if (requiresStaff(path) && !isStaff(role)) {
            throw new BusinessException(403, "无权限");
        }
        if (requiresUser(path) && !isUser(role)) {
            throw new BusinessException(403, "无权限");
        }
    }

    private boolean requiresAdmin(String path) {
        return path.startsWith("/api/user/list")
                || path.startsWith("/api/user/status")
                || path.startsWith("/api/pricing")
                || path.startsWith("/api/credit/adjust");
    }

    private boolean requiresStaff(String path) {
        if (requiresAdmin(path)) {
            return false;
        }
        return path.startsWith("/api/statistics")
                || path.startsWith("/api/bike/list")
                || path.matches("/api/bike/\\d+")
                || (path.equals("/api/bike") || path.startsWith("/api/bike/dispatch"))
                || path.startsWith("/api/station/list")
                || path.matches("/api/station/\\d+")
                || path.equals("/api/station")
                || path.startsWith("/api/ride/all")
                || path.startsWith("/api/fault/list")
                || path.startsWith("/api/fault/handle")
                || path.startsWith("/api/feedback/list")
                || path.startsWith("/api/feedback/reply")
                || (path.startsWith("/api/announcement") && !path.startsWith("/api/announcement/list"));
    }

    private boolean requiresUser(String path) {
        return path.startsWith("/api/wallet")
                || path.startsWith("/api/ride/start")
                || path.startsWith("/api/ride/end")
                || path.startsWith("/api/ride/current")
                || path.startsWith("/api/ride/list")
                || path.equals("/api/fault")
                || path.startsWith("/api/fault/my")
                || path.equals("/api/feedback")
                || path.startsWith("/api/feedback/my")
                || path.startsWith("/api/credit/score")
                || path.startsWith("/api/credit/records");
    }

    private boolean isAdmin(String role) {
        return "admin".equals(role);
    }

    private boolean isStaff(String role) {
        return "admin".equals(role) || "operator".equals(role);
    }

    private boolean isUser(String role) {
        return "user".equals(role);
    }
}
