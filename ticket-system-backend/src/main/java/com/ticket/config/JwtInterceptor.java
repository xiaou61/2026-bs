package com.ticket.config;

import com.ticket.common.BusinessException;
import com.ticket.utils.JwtUtils;
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

        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "token无效");
        }

        Long userId = jwtUtils.getUserIdFromToken(token);
        request.setAttribute("userId", userId);
        
        return true;
    }
}
