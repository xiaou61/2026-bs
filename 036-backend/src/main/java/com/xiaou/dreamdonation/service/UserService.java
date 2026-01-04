package com.xiaou.dreamdonation.service;

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
    public Map&lt;String, Object&gt; register(RegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        if (dto.getEmail() != null &amp;&amp; userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("邮箱已被注册");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(PasswordUtil.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRealName(dto.getRealName());
        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Map&lt;String, Object&gt; result = new HashMap&lt;&gt;();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public Map&lt;String, Object&gt; login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -&gt; new RuntimeException("用户名或密码错误"));

        if (!PasswordUtil.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new RuntimeException("账号已被禁用");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Map&lt;String, Object&gt; result = new HashMap&lt;&gt;();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -&gt; new RuntimeException("用户不存在"));
    }

    public User getUserInfo(Long userId) {
        return getUserById(userId);
    }
}
