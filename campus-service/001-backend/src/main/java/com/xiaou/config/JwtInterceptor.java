package com.xiaou.config;

import com.alibaba.fastjson2.JSON;
import com.xiaou.common.Result;
import com.xiaou.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器
 * @author xiaou
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS请求直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        // 获取token
        String token = request.getHeader("Authorization");
        
        if (token == null || token.isEmpty()) {
            log.warn("请求未携带token");
            sendError(response, 401, "未登录，请先登录");
            return false;
        }
        
        // 去除Bearer前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        // 验证token
        if (!jwtUtil.validateToken(token)) {
            log.warn("token无效或已过期");
            sendError(response, 401, "登录已过期，请重新登录");
            return false;
        }
        
        // 将用户信息存入request
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);
        String role = jwtUtil.getRoleFromToken(token);
        
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);
        request.setAttribute("role", role);
        
        return true;
    }
    
    private void sendError(HttpServletResponse response, int code, String message) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(code);
        Result<?> result = Result.error(code, message);
        response.getWriter().write(JSON.toJSONString(result));
    }
}

