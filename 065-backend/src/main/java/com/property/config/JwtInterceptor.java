package com.property.config;

import com.property.common.BusinessException;
import com.property.entity.User;
import com.property.mapper.UserMapper;
import com.property.service.TokenStoreService;
import com.property.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private TokenStoreService tokenStoreService;

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
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
            if (!tokenStoreService.matches(userId, token)) {
                throw new BusinessException(401, "登录状态失效");
            }
            User user = userMapper.selectById(userId);
            if (user == null || user.getStatus() == null || user.getStatus() == 0) {
                throw new BusinessException(401, "账号不可用");
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
}
