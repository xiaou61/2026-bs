package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.BusinessException;
import com.repair.entity.User;
import com.repair.mapper.UserMapper;
import com.repair.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 64;
    private static final int MAX_PAGE_SIZE = 500;
    private static final Set<String> ROLES = Set.of("admin", "technician", "customer");

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, JwtUtils jwtUtils, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException("用户名或密码不能为空");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        String token = jwtUtils.generateToken(user.getId().toString());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", toSafeUser(user));
        return result;
    }

    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        return toSafeUser(user);
    }

    public Page<User> getList(int pageNum, int pageSize, String username, String role, Integer status) {
        int size = Math.min(pageSize, MAX_PAGE_SIZE);
        Page<User> page = new Page<>(pageNum, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(username)) {
            wrapper.like("username", username);
        }
        if (StringUtils.hasText(role)) {
            wrapper.eq("role", role);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<User> result = userMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::clearPassword);
        return result;
    }

    @Transactional
    public void register(User user) {
        registerInternal(user, false);
    }

    @Transactional
    public void registerByAdmin(User user) {
        registerInternal(user, true);
    }

    private void registerInternal(User user, boolean adminCreated) {
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
        validatePassword(user.getPassword());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(400, "用户名已存在");
        }
        if (!adminCreated) {
            user.setRole("customer");
        } else if (!StringUtils.hasText(user.getRole())) {
            user.setRole("customer");
        } else if (!ROLES.contains(user.getRole())) {
            throw new BusinessException(400, "角色不合法");
        }
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    @Transactional
    public void update(User user) {
        if (user.getId() == null) {
            throw new BusinessException(400, "用户ID不能为空");
        }
        if (StringUtils.hasText(user.getPassword())) {
            validatePassword(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMapper.updateById(user);
    }

    @Transactional
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    public User requireUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        return user;
    }

    public void requireAdmin(Long userId) {
        User user = requireUser(userId);
        if (!"admin".equals(user.getRole())) {
            throw new BusinessException(403, "需要管理员权限");
        }
    }

    public boolean isAdmin(Long userId) {
        return "admin".equals(requireUser(userId).getRole());
    }

    private User toSafeUser(User user) {
        if (user == null) {
            return null;
        }
        User copy = new User();
        copy.setId(user.getId());
        copy.setUsername(user.getUsername());
        copy.setRealName(user.getRealName());
        copy.setPhone(user.getPhone());
        copy.setEmail(user.getEmail());
        copy.setAvatar(user.getAvatar());
        copy.setRole(user.getRole());
        copy.setStatus(user.getStatus());
        copy.setCreateTime(user.getCreateTime());
        return copy;
    }

    private void clearPassword(User user) {
        if (user != null) {
            user.setPassword(null);
        }
    }

    private void validatePassword(String password) {
        if (!StringUtils.hasText(password) || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new BusinessException("密码长度必须在 " + MIN_PASSWORD_LENGTH + " 到 " + MAX_PASSWORD_LENGTH + " 位之间");
        }
    }
}
