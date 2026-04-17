package com.xiaou.studyroom.utils;

import com.xiaou.studyroom.exception.BusinessException;
import com.xiaou.studyroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    public Long getUserId(String token) {
        try {
            String actualToken = token.replace("Bearer ", "");
            return jwtUtil.getUserIdFromToken(actualToken);
        } catch (Exception e) {
            throw new BusinessException(401, "Token验证失败");
        }
    }

    public boolean isAdmin(String token) {
        return userService.isAdmin(getUserId(token));
    }

    public void requireAdmin(String token) {
        if (!isAdmin(token)) {
            throw new BusinessException(403, "无管理员权限");
        }
    }
}
