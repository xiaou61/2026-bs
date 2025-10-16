package com.xiaou.campusclub.config;

import com.xiaou.campusclub.util.JwtUtil;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }
        
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        try {
            if (jwtUtil.isTokenExpired(token)) {
                response.setStatus(401);
                return false;
            }
            
            Long userId = jwtUtil.getUserId(token);
            request.setAttribute("userId", userId);
            request.setAttribute("username", jwtUtil.getUsername(token));
            request.setAttribute("userType", jwtUtil.getType(token));
            
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}

