package com.hrm.config;

import com.hrm.common.BusinessException;
import com.hrm.service.RuntimeStoreService;
import com.hrm.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RuntimeStoreService runtimeStoreService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String token = normalizeToken(request.getHeader("Authorization"));
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(401, "未登录");
        }
        if (runtimeStoreService.isTokenInvalid(token)) {
            throw new BusinessException(401, "登录已失效");
        }
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "登录已过期");
        }
        request.setAttribute("userId", jwtUtils.getUserId(token));
        request.setAttribute("role", jwtUtils.getRole(token));
        return true;
    }

    private String normalizeToken(String authorization) {
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        if (authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return authorization;
    }
}
