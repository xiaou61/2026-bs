package com.xiaou.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.common.BusinessException;
import com.xiaou.common.Result;
import com.xiaou.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(401, "未登录或token已过期")));
            return false;
        }

        token = token.substring(7);
        
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(401, "token无效或已过期")));
            return false;
        }

        Claims claims = jwtUtil.parseToken(token);
        request.setAttribute("userId", claims.get("userId"));
        request.setAttribute("username", claims.get("username"));
        request.setAttribute("role", claims.get("role"));
        
        return true;
    }
}

