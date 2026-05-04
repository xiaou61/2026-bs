package com.repair.config;

import com.repair.common.BusinessException;
import com.repair.service.RuntimeStoreService;
import com.repair.utils.JwtUtils;
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
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(401, "未登录");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (runtimeStoreService.isTokenInvalid(token)) {
            throw new BusinessException(401, "token已失效");
        }
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "token无效");
        }
        String userId = jwtUtils.getUserIdFromToken(token);
        request.setAttribute("userId", userId);
        return true;
    }
}

