package com.xiaou.dreamdonation.service;

import com.xiaou.dreamdonation.common.BusinessException;
import com.xiaou.dreamdonation.dto.LoginDTO;
import com.xiaou.dreamdonation.dto.RegisterDTO;
import com.xiaou.dreamdonation.entity.User;
import com.xiaou.dreamdonation.repository.UserRepository;
import com.xiaou.dreamdonation.util.JwtUtil;
import com.xiaou.dreamdonation.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Map<String, Object> register(RegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw BusinessException.badRequest("用户名已存在");
        }
        if (dto.getEmail() != null && userRepository.existsByEmail(dto.getEmail())) {
            throw BusinessException.badRequest("邮箱已被注册");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(PasswordUtil.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRealName(dto.getRealName());
        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public Map<String, Object> login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> BusinessException.badRequest("用户名或密码错误"));

        if (!PasswordUtil.matches(dto.getPassword(), user.getPassword())) {
            throw BusinessException.badRequest("用户名或密码错误");
        }

        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw BusinessException.forbidden("账号已被禁用");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> BusinessException.badRequest("用户不存在"));
    }

    public User getUserInfo(Long userId) {
        return getUserById(userId);
    }

    public User getAuthenticatedUser(String authorization) {
        String token = jwtUtil.normalizeToken(authorization);
        if (token == null || jwtUtil.isTokenExpired(token)) {
            throw BusinessException.unauthorized();
        }
        return getUserById(jwtUtil.getUserIdFromToken(token));
    }
}
