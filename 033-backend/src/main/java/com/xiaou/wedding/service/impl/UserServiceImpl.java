package com.xiaou.wedding.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.xiaou.wedding.dto.LoginRequest;
import com.xiaou.wedding.dto.RegisterRequest;
import com.xiaou.wedding.entity.User;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.UserMapper;
import com.xiaou.wedding.service.UserService;
import com.xiaou.wedding.util.JwtUtil;
import com.xiaou.wedding.vo.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException("User not found");
        }

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid password");
        }

        if (user.getStatus() != 1) {
            throw new BusinessException("Account is disabled");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRealName(user.getRealName());
        response.setRole(user.getRole());
        response.setToken(token);

        return response;
    }

    @Override
    public void register(RegisterRequest request) {
        User existUser = userMapper.findByUsername(request.getUsername());
        if (existUser != null) {
            throw new BusinessException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword()));
        user.setRealName(request.getRealName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole("CUSTOMER");
        user.setStatus(1);

        userMapper.insert(user);
    }
}
