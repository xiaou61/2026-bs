package com.classic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.User;
import com.classic.mapper.UserMapper;
import com.classic.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new BusinessException("用户名或密码不能为空");
        }
        String inputUsername = username.trim();
        String inputPassword = password.trim();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, inputUsername));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!inputPassword.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == null || user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        userMapper.update(null, new LambdaUpdateWrapper<User>().eq(User::getId, user.getId()).set(User::getLastLoginTime, LocalDateTime.now()));
        String token = JwtUtils.generateToken(String.valueOf(user.getId()), user.getRole());
        redisTemplate.opsForValue().set("classic:token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", safeUser(user));
        return map;
    }

    public User register(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new BusinessException("用户名和密码不能为空");
        }
        String username = user.getUsername().trim();
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setUsername(username);
        user.setPassword(user.getPassword().trim());
        user.setNickname(user.getNickname() == null || user.getNickname().trim().isEmpty() ? username : user.getNickname().trim());
        user.setRole("USER");
        user.setStatus(1);
        userMapper.insert(user);
        return safeUser(user);
    }

    public User getById(Long id) {
        return safeUser(userMapper.selectById(id));
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
            throw new BusinessException("密码不能为空");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!oldPassword.trim().equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        if (oldPassword.trim().equals(newPassword.trim())) {
            throw new BusinessException("新旧密码不能一致");
        }
        userMapper.update(null, new LambdaUpdateWrapper<User>().eq(User::getId, userId).set(User::getPassword, newPassword.trim()));
        redisTemplate.delete("classic:token:" + userId);
    }

    public void updateProfile(Long userId, User profile) {
        if (profile == null || profile.getNickname() == null || profile.getNickname().trim().isEmpty()) {
            throw new BusinessException("昵称不能为空");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setNickname(profile.getNickname().trim());
        user.setPhone(profile.getPhone() == null ? null : profile.getPhone().trim());
        user.setEmail(profile.getEmail() == null ? null : profile.getEmail().trim());
        userMapper.updateById(user);
    }

    public void logout(Long userId) {
        redisTemplate.delete("classic:token:" + userId);
    }

    public PageResult<User> page(Integer pageNum, Integer pageSize, String username, String role, Integer status) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(username != null && !username.trim().isEmpty(), User::getUsername, username.trim());
        wrapper.eq(role != null && !role.trim().isEmpty(), User::getRole, role.trim());
        wrapper.eq(status != null, User::getStatus, status);
        wrapper.orderByDesc(User::getId);
        Page<User> page = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(item -> item.setPassword(null));
        PageResult<User> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(page.getRecords());
        return result;
    }

    public void save(User user) {
        if (user == null) {
            throw new BusinessException("参数不能为空");
        }
        if (user.getId() == null) {
            if (user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new BusinessException("用户名和密码不能为空");
            }
            String username = user.getUsername().trim();
            if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) != null) {
                throw new BusinessException("用户名已存在");
            }
            user.setUsername(username);
            user.setPassword(user.getPassword().trim());
            user.setNickname(user.getNickname() == null || user.getNickname().trim().isEmpty() ? username : user.getNickname().trim());
            user.setRole(user.getRole() == null || user.getRole().trim().isEmpty() ? "USER" : user.getRole().trim());
            if (!"ADMIN".equals(user.getRole()) && !"USER".equals(user.getRole())) {
                throw new BusinessException("角色不合法");
            }
            user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
            userMapper.insert(user);
        } else {
            User db = userMapper.selectById(user.getId());
            if (db == null) {
                throw new BusinessException("用户不存在");
            }
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                throw new BusinessException("用户名不能为空");
            }
            String username = user.getUsername().trim();
            User exist = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            if (exist != null && !exist.getId().equals(user.getId())) {
                throw new BusinessException("用户名已存在");
            }
            if (user.getNickname() == null || user.getNickname().trim().isEmpty()) {
                throw new BusinessException("昵称不能为空");
            }
            String oldRole = db.getRole();
            db.setUsername(username);
            db.setNickname(user.getNickname().trim());
            db.setPhone(user.getPhone() == null ? null : user.getPhone().trim());
            db.setEmail(user.getEmail() == null ? null : user.getEmail().trim());
            String role = user.getRole() == null || user.getRole().trim().isEmpty() ? db.getRole() : user.getRole().trim();
            if (!"ADMIN".equals(role) && !"USER".equals(role)) {
                throw new BusinessException("角色不合法");
            }
            db.setRole(role);
            db.setStatus(user.getStatus() == null ? db.getStatus() : user.getStatus());
            boolean resetToken = false;
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                db.setPassword(user.getPassword().trim());
                resetToken = true;
            }
            if (!oldRole.equals(role)) {
                resetToken = true;
            }
            userMapper.updateById(db);
            if (resetToken) {
                redisTemplate.delete("classic:token:" + db.getId());
            }
        }
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        userMapper.update(null, new LambdaUpdateWrapper<User>().eq(User::getId, id).set(User::getStatus, status));
        if (status == 0) {
            redisTemplate.delete("classic:token:" + id);
        }
    }

    public void deleteById(Long id) {
        if (id == 1L) {
            throw new BusinessException("默认管理员不可删除");
        }
        userMapper.deleteById(id);
        redisTemplate.delete("classic:token:" + id);
    }

    public Long countAll() {
        return userMapper.selectCount(new LambdaQueryWrapper<>());
    }

    private User safeUser(User user) {
        if (user == null) {
            return null;
        }
        user.setPassword(null);
        return user;
    }
}
