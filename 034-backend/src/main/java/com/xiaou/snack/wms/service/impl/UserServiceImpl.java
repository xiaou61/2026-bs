package com.xiaou.snack.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.snack.wms.common.ApiException;
import com.xiaou.snack.wms.entity.basic.User;
import com.xiaou.snack.wms.mapper.UserMapper;
import com.xiaou.snack.wms.security.JwtService;
import com.xiaou.snack.wms.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String username, String password) {
        User user = lambdaQuery().eq(User::getUsername, username).one();
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException(401, "invalid credentials");
        }
        String role = user.getRoleId() != null ? user.getRoleId().toString() : "user";
        return jwtService.generateToken(username, role);
    }
}
