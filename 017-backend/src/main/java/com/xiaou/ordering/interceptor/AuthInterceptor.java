package com.xiaou.ordering.interceptor;

import com.xiaou.ordering.common.BusinessException;
import com.xiaou.ordering.util.JwtUtil;
import com.xiaou.ordering.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public AuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = resolveToken(request);
        if (token == null || token.isBlank()) {
            throw new BusinessException("未登录或登录已过期");
        }

        try {
            Long userId = jwtUtil.getUserIdFromToken(token);
            String userType = jwtUtil.getUserTypeFromToken(token);
            UserContext.setCurrentUserId(userId);
            UserContext.setCurrentUserType(userType);
            verifyRole(request.getRequestURI(), userType);
            return true;
        } catch (BusinessException e) {
            UserContext.clear();
            throw e;
        } catch (Exception e) {
            UserContext.clear();
            throw new BusinessException("未登录或登录已过期");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }

    private void verifyRole(String requestUri, String userType) {
        if (requestUri.startsWith("/api/admin/") && !"admin".equals(userType)) {
            throw new BusinessException("无管理员权限");
        }
        if (requestUri.startsWith("/api/merchant/") && !"merchant".equals(userType)) {
            throw new BusinessException("无商家权限");
        }
        if ((requestUri.startsWith("/api/cart") || requestUri.startsWith("/api/orders")) && !"user".equals(userType)) {
            throw new BusinessException("仅学生用户可访问");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && !authorization.isBlank()) {
            if (authorization.startsWith("Bearer ")) {
                return authorization.substring(7);
            }
            return authorization;
        }
        return request.getHeader("token");
    }
}
