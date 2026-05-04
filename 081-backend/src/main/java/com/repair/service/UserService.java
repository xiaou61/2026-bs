package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.BusinessException;
import com.repair.entity.User;
import com.repair.mapper.UserMapper;
import com.repair.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    private static final Set<String> ROLES = Set.of("admin", "technician", "customer");

    public Map<String, Object> login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", password);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        String token = jwtUtils.generateToken(user.getId().toString());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", user);
        return result;
    }

    public User getUserInfo(Long userId) {
        return userMapper.selectById(userId);
    }

    public Page<User> getList(int pageNum, int pageSize, String username, String role, Integer status) {
        Page<User> page = new Page<>(pageNum, pageSize);
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
        return userMapper.selectPage(page, wrapper);
    }

    public void register(User user) {
        registerInternal(user, false);
    }

    public void registerByAdmin(User user) {
        registerInternal(user, true);
    }

    private void registerInternal(User user, boolean adminCreated) {
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
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
        userMapper.insert(user);
    }

    public void update(User user) {
        userMapper.updateById(user);
    }

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
}
