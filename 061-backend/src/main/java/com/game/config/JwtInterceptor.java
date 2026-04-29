package com.game.config;

import cn.hutool.core.util.StrUtil;
import com.game.common.BusinessException;
import com.game.entity.User;
import com.game.mapper.UserMapper;
import com.game.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StrUtil.isBlank(token)) {
            throw new BusinessException(401, "未登录");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            if (JwtUtils.isTokenExpired(token)) {
                throw new BusinessException(401, "登录已过期");
            }
            Long userId = Long.parseLong(JwtUtils.getUserIdFromToken(token));
            User user = userMapper.selectById(userId);
            if (user == null || user.getStatus() == null || user.getStatus() != 1) {
                throw new BusinessException(401, "用户不存在或已禁用");
            }
            Object cacheToken = getCachedToken(userId);
            if (cacheToken != null && !token.equals(cacheToken.toString())) {
                throw new BusinessException(401, "登录状态失效");
            }
            request.setAttribute("userId", userId);
            request.setAttribute("role", user.getRole());
            return true;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(401, "token无效");
        }
    }

    private Object getCachedToken(Long userId) {
        try {
            return redisTemplate.opsForValue().get("game:token:" + userId);
        } catch (RuntimeException ignored) {
            return null;
        }
    }
}
