package com.bike.config;

import com.bike.common.BusinessException;
import com.bike.utils.JwtUtils;
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
        try {
            if (jwtUtils.isTokenExpired(token)) {
                throw new BusinessException(401, "登录已过期");
            }
            String userId = jwtUtils.getUserIdFromToken(token);
            String role = jwtUtils.getRoleFromToken(token);
            request.setAttribute("userId", Long.parseLong(userId));
            request.setAttribute("role", role);
            return true;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(401, "无效的token");
        }
    }
}
