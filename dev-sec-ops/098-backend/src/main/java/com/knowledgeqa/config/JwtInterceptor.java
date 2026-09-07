package com.knowledgeqa.config;

import com.knowledgeqa.common.BusinessException;
import com.knowledgeqa.service.TokenService;
import com.knowledgeqa.utils.JwtUtils;
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
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            throw new BusinessException(401, "未登录");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "登录已过期");
        }
        Long userId = jwtUtils.getUserId(token);
        if (!tokenService.isActive(userId, token)) {
            throw new BusinessException(401, "登录已失效");
        }
        request.setAttribute("userId", userId);
        request.setAttribute("role", jwtUtils.getRole(token));
        return true;
    }
}
