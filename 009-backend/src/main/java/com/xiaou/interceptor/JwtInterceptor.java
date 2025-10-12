package com.xiaou.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.common.Result;
import com.xiaou.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            Result<?> result = Result.error(401, "未登录或token已过期");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
            return false;
        }

        try {
            JwtUtil.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            Result<?> result = Result.error(401, "token无效或已过期");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
            return false;
        }
    }
}

