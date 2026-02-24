package com.harbin.tourism.config;

import com.harbin.tourism.common.BusinessException;
import com.harbin.tourism.utils.JwtUtils;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(401, "未登录，请先登录");
        }
        if (!jwtUtils.isTokenValid(token)) {
            throw new BusinessException(401, "Token已过期，请重新登录");
        }
        request.setAttribute("userId", jwtUtils.getUserId(token));
        request.setAttribute("role", jwtUtils.getRole(token));
        return true;
    }
}
