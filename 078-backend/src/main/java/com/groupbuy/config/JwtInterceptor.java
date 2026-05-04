package com.groupbuy.config;

import com.groupbuy.common.BusinessException;
import com.groupbuy.service.RuntimeStoreService;
import com.groupbuy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
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
            if (jwtUtils.isTokenExpired(token)) {
                throw new BusinessException(401, "登录已过期");
            }
            Long userId = jwtUtils.getUserId(token);
            if (!runtimeStoreService.isActiveToken(userId, token)) {
                throw new BusinessException(401, "登录状态失效");
            }
            Integer role = jwtUtils.getRole(token);
            request.setAttribute("userId", userId);
            request.setAttribute("role", role);
            return true;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(401, "无效的token");
        }
    }
}
