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
            request.setAttribute("userId", userId);
            request.setAttribute("userType", jwtUtils.getType(token));
            return true;
        } catch (Exception e) {
            throw new BusinessException(401, "无效的token");
        }
    }
}
