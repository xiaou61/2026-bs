package com.xiaou.ailearning.interceptor;

import com.xiaou.ailearning.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPublicApi(request)) {
            return true;
        }

        // 获取token
        String token = request.getHeader("Authorization");
        
        if (token == null || !token.startsWith("Bearer ")) {
            token = request.getParameter("token");
        } else {
            token = token.substring(7);
        }

        if (token != null && jwtUtil.validateToken(token)) {
            // 从token中获取用户信息，设置到request中
            Long userId = jwtUtil.getUserIdFromToken(token);
            String username = jwtUtil.getUsernameFromToken(token);
            
            request.setAttribute("userId", userId);
            request.setAttribute("username", username);
            
            return true;
        }

        // token无效，返回401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"未授权访问\"}");
        
        return false;
    }

    private boolean isPublicApi(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        if (!"GET".equalsIgnoreCase(method)) {
            return false;
        }

        return "/api/course/hot".equals(uri)
                || "/api/course/list".equals(uri)
                || uri.startsWith("/api/course/category/")
                || uri.matches("^/api/course/\\d+$")
                || "/api/user/check-username".equals(uri)
                || "/api/user/check-email".equals(uri)
                || uri.matches("^/api/course/detail/\\d+$")
                || uri.matches("^/api/course/\\d+/knowledge-points$")
                || uri.matches("^/api/course/\\d+/learning-path$")
                || uri.matches("^/api/course/\\d+/comments$")
                || uri.matches("^/api/recommendation/related-courses/\\d+$")
                || uri.matches("^/api/learning-record/course/\\d+/stats$");
    }
}
