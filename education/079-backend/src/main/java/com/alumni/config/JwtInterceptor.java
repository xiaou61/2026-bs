package com.alumni.config;

import com.alumni.common.BusinessException;
import com.alumni.service.RuntimeStoreService;
import com.alumni.utils.JwtUtils;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (token == null || token.isEmpty()) {
            if (isPublicGet(request)) {
                return true;
            }
            throw new BusinessException(401, "请先登录");
        }
        if (!jwtUtils.validateToken(token) || !runtimeStoreService.isTokenActive(token)) {
            if (isPublicGet(request)) {
                return true;
            }
            throw new BusinessException(401, "登录已过期");
        }
        request.setAttribute("userId", jwtUtils.getUserId(token));
        request.setAttribute("username", jwtUtils.getUsername(token));
        request.setAttribute("role", jwtUtils.getRole(token));
        request.setAttribute("token", token);
        return true;
    }

    private boolean isPublicGet(HttpServletRequest request) {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String uri = request.getRequestURI();
        return uri.equals("/api/grade/list")
                || uri.equals("/api/class/list")
                || uri.equals("/api/news/list")
                || uri.matches("/api/news/\\d+")
                || uri.matches("/api/news/\\d+/comments")
                || uri.equals("/api/activity/list")
                || uri.matches("/api/activity/\\d+")
                || uri.matches("/api/activity/\\d+/photos")
                || uri.equals("/api/banner/list")
                || uri.equals("/api/forum/category/list")
                || uri.equals("/api/forum/post/list")
                || uri.matches("/api/forum/post/\\d+")
                || uri.matches("/api/forum/post/\\d+/replies")
                || uri.equals("/api/job/list")
                || uri.matches("/api/job/\\d+")
                || uri.equals("/api/company/list")
                || uri.matches("/api/company/\\d+")
                || uri.equals("/api/donation/project/list")
                || uri.matches("/api/donation/project/\\d+");
    }
}
