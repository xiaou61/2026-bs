package com.security.config;

import com.security.common.BusinessException;
import com.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String path = request.getRequestURI();
        if (isPublicPath(path)) {
            attachOptionalToken(request);
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new BusinessException(401, "未登录");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            if (jwtUtils.isTokenExpired(token)) {
                throw new BusinessException(401, "登录已过期");
            }
            Long userId = jwtUtils.getUserId(token);
            String userType = jwtUtils.getType(token);
            if (path.startsWith("/api/admin") && !"admin".equals(userType)) {
                throw new BusinessException(403, "无权访问管理端接口");
            }
            request.setAttribute("userId", userId);
            request.setAttribute("userType", userType);
            return true;
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            }
            throw new BusinessException(401, "无效的token");
        }
    }

    private boolean isPublicPath(String path) {
        return "/api/user/login".equals(path)
                || "/api/admin/login".equals(path)
                || "/api/category/list".equals(path)
                || "/api/article/list".equals(path)
                || path.matches("/api/article/\\d+")
                || "/api/news/list".equals(path)
                || path.matches("/api/news/\\d+")
                || "/api/qa/list".equals(path)
                || path.matches("/api/qa/\\d+")
                || "/api/rank/list".equals(path);
    }

    private void attachOptionalToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            return;
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            if (!jwtUtils.isTokenExpired(token)) {
                request.setAttribute("userId", jwtUtils.getUserId(token));
                request.setAttribute("userType", jwtUtils.getType(token));
            }
        } catch (Exception ignored) {
            request.removeAttribute("userId");
            request.removeAttribute("userType");
        }
    }
}
