package com.xiaou.ticket.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.xiaou.ticket.dto.RegisterRequest;
import com.xiaou.ticket.entity.User;
import com.xiaou.ticket.repository.UserRepository;
import com.xiaou.ticket.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> login(String username, String password) {
        if (StrUtil.hasBlank(username, password)) {
            throw new RuntimeException("用户名和密码不能为空");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new RuntimeException("账号已被禁用");
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public User register(RegisterRequest request) {
        if (request == null || StrUtil.hasBlank(request.getUsername(), request.getPassword(), request.getPhone())) {
            throw new RuntimeException("用户名、密码和手机号不能为空");
        }
        if (StrUtil.isNotBlank(request.getConfirmPassword()) && !request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRealName(request.getRealName());
        user.setIdCard(request.getIdCard());
        user.setRole("USER");
        user.setBalance(BigDecimal.ZERO);
        user.setStatus("ACTIVE");
        return userRepository.save(user);
    }
}
