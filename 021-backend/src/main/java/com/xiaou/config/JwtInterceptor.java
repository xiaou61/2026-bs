package com.xiaou.config;

import com.xiaou.exception.BusinessException;
import com.xiaou.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域预检请求直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 从请求头获取token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BusinessException(401, "请先登录");
        }

        token = token.substring(7); // 去掉"Bearer "前缀

        try {
            // 验证token
            if (jwtUtil.isTokenExpired(token)) {
                throw new BusinessException(401, "登录已过期，请重新登录");
            }

            // 将用户信息存入请求域
            String username = jwtUtil.getUsernameFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);

            request.setAttribute("username", username);
            request.setAttribute("userId", userId);

            return true;
        } catch (Exception e) {
            throw new BusinessException(401, "token无效");
        }
    }
}