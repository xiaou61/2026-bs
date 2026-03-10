package com.eldercare.config;

import com.eldercare.common.BusinessException;
import com.eldercare.utils.JwtUtils;
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
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new BusinessException(401, "未登录");
        }
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "token无效");
        }
        String userId = jwtUtils.getUserIdFromToken(token);
        String cacheToken = stringRedisTemplate.opsForValue().get("TOKEN:" + userId);
        if (cacheToken == null || !cacheToken.equals(token)) {
            throw new BusinessException(401, "登录已过期");
        }
        request.setAttribute("userId", userId);
        return true;
    }
}
