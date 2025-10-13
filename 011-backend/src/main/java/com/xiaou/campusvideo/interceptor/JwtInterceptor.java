package com.xiaou.campusvideo.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.campusvideo.util.JwtUtil;
import com.xiaou.campusvideo.util.Result;
import com.xiaou.campusvideo.util.UserHolder;
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
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            Result<String> result = Result.error(401, "未登录或登录已过期");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
            return false;
        }
        
        try {
            Claims claims = jwtUtil.parseToken(token);
            if (claims == null || jwtUtil.isTokenExpired(token)) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                Result<String> result = Result.error(401, "登录已过期，请重新登录");
                response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                return false;
            }
            
            Long userId = claims.get("userId", Long.class);
            UserHolder.setUserId(userId);
            return true;
            
        } catch (Exception e) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            Result<String> result = Result.error(401, "Token验证失败");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
            return false;
        }
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUserId();
    }
}

