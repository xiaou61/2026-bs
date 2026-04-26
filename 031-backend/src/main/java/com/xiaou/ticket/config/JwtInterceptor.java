package com.xiaou.ticket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.ticket.dto.Result;
import com.xiaou.ticket.util.AuthContext;
import com.xiaou.ticket.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public JwtInterceptor(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeUnauthorized(response, "未登录或登录已过期");
            return false;
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            writeUnauthorized(response, "登录令牌无效");
            return false;
        }

        AuthContext.set(new AuthContext.AuthUser(
                jwtUtil.getUserId(token),
                jwtUtil.getUsername(token),
                jwtUtil.parseToken(token).get("role", String.class)
        ));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        AuthContext.clear();
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(401, message)));
    }
}
