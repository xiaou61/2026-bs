package com.gongkao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.BusinessException;
import com.gongkao.entity.User;
import com.gongkao.mapper.UserMapper;
import com.gongkao.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    private static final Set<String> MANAGEABLE_ROLES = Set.of("admin", "teacher", "student");

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

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
        String token = jwtUtils.generateToken(String.valueOf(user.getId()));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userInfo", user);
        return map;
    }

    public void register(User user) {
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole("student");
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        userMapper.insert(user);
    }

    public void registerByAdmin(User user) {
        if (!StringUtils.hasText(user.getRole()) || !MANAGEABLE_ROLES.contains(user.getRole())) {
            throw new BusinessException("角色不合法");
        }
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        registerUser(user);
    }

    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return user;
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

    public void add(User user) {
        registerByAdmin(user);
    }

    public void update(User user) {
        if (user.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (user.getRole() != null && !MANAGEABLE_ROLES.contains(user.getRole())) {
            throw new BusinessException("角色不合法");
        }
        userMapper.updateById(user);
    }

    public void updateProfile(Long userId, User payload) {
        User update = new User();
        update.setId(userId);
        update.setRealName(payload.getRealName());
        update.setPhone(payload.getPhone());
        update.setAvatar(payload.getAvatar());
        if (StringUtils.hasText(payload.getPassword())) {
            update.setPassword(payload.getPassword());
        }
        userMapper.updateById(update);
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    public void requireAdmin(Long userId) {
        User user = getUserInfo(userId);
        if (!"admin".equalsIgnoreCase(user.getRole())) {
            throw new BusinessException(403, "无权访问");
        }
    }

    private void registerUser(User user) {
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }
        userMapper.insert(user);
    }
}
