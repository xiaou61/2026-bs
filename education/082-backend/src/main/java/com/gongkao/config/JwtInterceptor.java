package com.gongkao.config;

import com.gongkao.common.BusinessException;
import com.gongkao.entity.User;
import com.gongkao.mapper.UserMapper;
import com.gongkao.service.RuntimeStoreService;
import com.gongkao.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RuntimeStoreService runtimeStoreService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(401, "未登录");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (runtimeStoreService.isTokenInvalid(token)) {
            throw new BusinessException(401, "token已失效");
        }
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "token无效");
        }
        String userId = jwtUtils.getUserIdFromToken(token);
        User user = userMapper.selectById(Long.valueOf(userId));
        if (user == null || (user.getStatus() != null && user.getStatus() == 0)) {
            throw new BusinessException(401, "账号不可用");
        }
        request.setAttribute("userId", userId);
        request.setAttribute("role", user.getRole());
        return true;
    }
}
