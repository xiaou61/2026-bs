package com.oa.config;

import com.oa.common.BusinessException;
import com.oa.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new BusinessException(401, "未登录");
        }
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "token无效或已过期");
        }
        request.setAttribute("userId", jwtUtils.getUserId(token));
        request.setAttribute("role", jwtUtils.getRole(token));
        return true;
    }
}
