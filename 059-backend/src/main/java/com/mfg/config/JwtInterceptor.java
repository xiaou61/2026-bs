package com.mfg.config;

import cn.hutool.core.util.StrUtil;
import com.mfg.common.BusinessException;
import com.mfg.utils.JwtUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    public JwtInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (StrUtil.isBlank(token)) {
            throw new BusinessException(401, "未登录");
        }
        try {
            String userId = JwtUtils.getUserIdFromToken(token);
            String redisToken = redisTemplate.opsForValue().get("token:" + userId);
            if (redisToken == null || !redisToken.equals(token)) {
                throw new BusinessException(401, "token已过期");
            }
            request.setAttribute("userId", userId);
            return true;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(401, "token无效");
        }
    }
}
