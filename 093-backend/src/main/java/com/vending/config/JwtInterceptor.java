package com.vending.config;

import com.vending.common.BusinessException;
import com.vending.service.RuntimeStoreService;
import com.vending.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private RuntimeStoreService runtimeStoreService;

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
            if (!JwtUtils.validateToken(token)) {
                throw new BusinessException(401, "登录已过期");
            }
            Long userId = JwtUtils.getUserId(token);
            if (!runtimeStoreService.isValidToken(userId, token)) {
                throw new BusinessException(401, "登录状态失效");
            }
            request.setAttribute("userId", userId);
            request.setAttribute("role", JwtUtils.getRole(token));
            return true;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(401, "token无效");
        }
    }
}
