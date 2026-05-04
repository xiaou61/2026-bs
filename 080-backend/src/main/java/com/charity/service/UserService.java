package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.User;
import com.charity.mapper.UserMapper;
import com.charity.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

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
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        String token = jwtUtils.generateToken(user.getId().toString(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", user);
        return result;
    }

    public User getUserInfo(Long userId) {
        return requireActiveUser(userId);
    }

    public Page<User> getList(int pageNum, int pageSize, String username, String role) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            wrapper.like("username", username);
        }
        if (role != null && !role.isEmpty()) {
            wrapper.eq("role", role);
        }
        return userMapper.selectPage(page, wrapper);
    }

    public void register(User user) {
        if (!StringUtils.hasText(user.getRole()) || "admin".equals(user.getRole())) {
            user.setRole("donor");
        }
        add(user);
    }

    public void add(User user) {
        validateForSave(user);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        userMapper.insert(user);
    }

    public void update(User user, Long currentUserId) {
        if (user.getId() == null) {
            throw new BusinessException(400, "用户ID不能为空");
        }
        User currentUser = requireActiveUser(currentUserId);
        User existing = userMapper.selectById(user.getId());
        if (existing == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if (!isAdmin(currentUser) && !Objects.equals(currentUser.getId(), user.getId())) {
            throw new BusinessException(403, "无权限操作该用户");
        }
        if (!isAdmin(currentUser)) {
            user.setRole(existing.getRole());
            user.setStatus(existing.getStatus());
        }
        if (!StringUtils.hasText(user.getPassword())) {
            user.setPassword(existing.getPassword());
        }
        if (!StringUtils.hasText(user.getUsername())) {
            user.setUsername(existing.getUsername());
        }
        userMapper.updateById(user);
    }

    public void delete(Long id, Long currentUserId) {
        requireAdmin(currentUserId);
        if (Objects.equals(id, currentUserId)) {
            throw new BusinessException(400, "不能删除当前登录用户");
        }
        userMapper.deleteById(id);
    }

    public User requireActiveUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(401, "用户不存在或已失效");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException(403, "账号已被禁用");
        }
        return user;
    }

    public User requireAdmin(Long userId) {
        User user = requireActiveUser(userId);
        if (!isAdmin(user)) {
            throw new BusinessException(403, "需要管理员权限");
        }
        return user;
    }

    public User requireAnyRole(Long userId, String... roles) {
        User user = requireActiveUser(userId);
        if (isAdmin(user)) {
            return user;
        }
        if (Arrays.stream(roles).noneMatch(role -> role.equals(user.getRole()))) {
            throw new BusinessException(403, "无权限访问");
        }
        return user;
    }

    public boolean isAdmin(User user) {
        return user != null && "admin".equals(user.getRole());
    }

    public boolean isDonor(User user) {
        return user != null && "donor".equals(user.getRole());
    }

    public boolean isVolunteer(User user) {
        return user != null && "volunteer".equals(user.getRole());
    }

    private void validateForSave(User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            throw new BusinessException(400, "用户名不能为空");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new BusinessException(400, "密码不能为空");
        }
        if (!StringUtils.hasText(user.getRole())) {
            user.setRole("donor");
        }
        if (!Arrays.asList("admin", "donor", "volunteer").contains(user.getRole())) {
            throw new BusinessException(400, "角色不合法");
        }
    }
}
