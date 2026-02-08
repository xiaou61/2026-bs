package com.movie.config;

import com.movie.common.BusinessException;
import com.movie.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new BusinessException(401, "未登录");
        }
        try {
            String userId = JwtUtils.getUserIdFromToken(token);
            String role = JwtUtils.getRoleFromToken(token);
            request.setAttribute("userId", Long.parseLong(userId));
            request.setAttribute("role", role);
            return true;
        } catch (Exception e) {
            throw new BusinessException(401, "token无效");
        }
    }
}
