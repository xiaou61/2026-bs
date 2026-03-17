package com.kindergarten.config;

import com.kindergarten.common.BusinessException;
import com.kindergarten.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
            throw new BusinessException(401, "登录已过期");
        }
        Long userId = jwtUtils.getUserId(token);
        String cacheToken = stringRedisTemplate.opsForValue().get("TOKEN:" + userId);
        if (cacheToken == null || !cacheToken.equals(token)) {
            throw new BusinessException(401, "登录已失效");
        }
        request.setAttribute("userId", userId);
        request.setAttribute("role", jwtUtils.getRole(token));
        return true;
    }
}
