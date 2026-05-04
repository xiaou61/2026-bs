package com.teachres.config;

import com.teachres.common.BusinessException;
import com.teachres.service.RuntimeStoreService;
import com.teachres.utils.JwtUtils;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new BusinessException(401, "未登录");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "登录已过期");
        }
        Long userId = jwtUtils.getUserId(token);
        if (!runtimeStoreService.isTokenActive(userId, token)) {
            throw new BusinessException(401, "登录已失效");
        }
        request.setAttribute("userId", userId);
        request.setAttribute("role", jwtUtils.getRole(token));
        return true;
    }
}
