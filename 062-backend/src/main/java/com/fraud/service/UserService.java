package com.fraud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.entity.User;
import com.fraud.mapper.UserMapper;
import com.fraud.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        username = username.trim();
        password = password.trim();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == null || user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);
        String token = JwtUtils.generateToken(String.valueOf(user.getId()), user.getRole());
        redisTemplate.opsForValue().set("fraud:token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", safeUser(user));
        return map;
    }

    public User register(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new BusinessException("用户名和密码不能为空");
        }
        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        if (user.getUsername().length() > 50) {
            throw new BusinessException("用户名不能超过50字符");
        }
        if (user.getPassword().length() > 100) {
            throw new BusinessException("密码不能超过100字符");
        }
        User exist = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        if (user.getNickname() == null || user.getNickname().trim().isEmpty()) {
            user.setNickname(user.getUsername());
        } else {
            user.setNickname(user.getNickname().trim());
        }
        if (user.getNickname().length() > 50) {
            throw new BusinessException("昵称不能超过50字符");
        }
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
        oldPassword = oldPassword.trim();
        newPassword = newPassword.trim();
        if (oldPassword.equals(newPassword)) {
            throw new BusinessException("新旧密码不能一致");
        }
        if (newPassword.length() > 100) {
            throw new BusinessException("密码不能超过100字符");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getPassword().equals(oldPassword)) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(newPassword);
        userMapper.updateById(user);
        redisTemplate.delete("fraud:token:" + userId);
    }

    public void updateProfile(Long userId, User profile) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (profile == null || profile.getNickname() == null || profile.getNickname().trim().isEmpty()) {
            throw new BusinessException("昵称不能为空");
        }
        String nickname = profile.getNickname().trim();
        if (nickname.length() > 50) {
            throw new BusinessException("昵称不能超过50字符");
        }
        String avatar = profile.getAvatar() == null ? null : profile.getAvatar().trim();
        if (avatar != null && avatar.length() > 255) {
            throw new BusinessException("头像地址不能超过255字符");
        }
        String phone = profile.getPhone() == null ? null : profile.getPhone().trim();
        if (phone != null && phone.length() > 20) {
            throw new BusinessException("手机号不能超过20字符");
        }
        String email = profile.getEmail() == null ? null : profile.getEmail().trim();
        if (email != null && email.length() > 100) {
            throw new BusinessException("邮箱不能超过100字符");
        }
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setPhone(phone);
        user.setEmail(email);
        userMapper.updateById(user);
    }

    public void logout(Long userId) {
        redisTemplate.delete("fraud:token:" + userId);
    }

    public Page<User> page(Integer pageNum, Integer pageSize, String username, String role, Integer status) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (username != null && !username.trim().isEmpty()) {
            wrapper.like("username", username.trim());
        }
        if (role != null && !role.trim().isEmpty()) {
            wrapper.eq("role", role.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("id");
        Page<User> page = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(item -> item.setPassword(null));
        return page;
    }

    public List<User> riskUserList() {
        List<User> list = userMapper.selectList(new QueryWrapper<User>()
                .in("role", Arrays.asList("ADMIN", "ANALYST"))
                .eq("status", 1)
                .orderByAsc("id"));
        list.forEach(item -> item.setPassword(null));
        return list;
    }

    public void save(User user) {
        if (user == null) {
            throw new BusinessException("用户参数不能为空");
        }
        if (user.getId() == null) {
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                throw new BusinessException("用户名不能为空");
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new BusinessException("密码不能为空");
            }
            user.setUsername(user.getUsername().trim());
            user.setPassword(user.getPassword().trim());
            if (user.getUsername().length() > 50) {
                throw new BusinessException("用户名不能超过50字符");
            }
            if (user.getPassword().length() > 100) {
                throw new BusinessException("密码不能超过100字符");
            }
            if (user.getNickname() == null || user.getNickname().trim().isEmpty()) {
                user.setNickname(user.getUsername());
            } else {
                user.setNickname(user.getNickname().trim());
            }
            if (user.getNickname().length() > 50) {
                throw new BusinessException("昵称不能超过50字符");
            }
            User exist = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
            if (exist != null) {
                throw new BusinessException("用户名已存在");
            }
            if (user.getRole() == null || user.getRole().trim().isEmpty()) {
                user.setRole("USER");
            } else {
                user.setRole(user.getRole().trim());
                if (!"ADMIN".equals(user.getRole()) && !"ANALYST".equals(user.getRole()) && !"USER".equals(user.getRole())) {
                    throw new BusinessException("角色不合法");
                }
            }
            if (user.getStatus() == null) {
                user.setStatus(1);
            }
            userMapper.insert(user);
        } else {
            User db = userMapper.selectById(user.getId());
            if (db == null) {
                throw new BusinessException("用户不存在");
            }
            boolean passwordChanged = false;
            boolean roleChanged = false;
            if (user.getUsername() != null) {
                user.setUsername(user.getUsername().trim());
                if (user.getUsername().isEmpty()) {
                    throw new BusinessException("用户名不能为空");
                }
                if (user.getUsername().length() > 50) {
                    throw new BusinessException("用户名不能超过50字符");
                }
                Long count = userMapper.selectCount(new QueryWrapper<User>().eq("username", user.getUsername()).ne("id", user.getId()));
                if (count != null && count > 0) {
                    throw new BusinessException("用户名已存在");
                }
            }
            if (user.getNickname() != null) {
                user.setNickname(user.getNickname().trim());
                if (user.getNickname().isEmpty()) {
                    throw new BusinessException("昵称不能为空");
                }
                if (user.getNickname().length() > 50) {
                    throw new BusinessException("昵称不能超过50字符");
                }
            }
            if (user.getPhone() != null) {
                user.setPhone(user.getPhone().trim());
                if (user.getPhone().length() > 20) {
                    throw new BusinessException("手机号不能超过20字符");
                }
            }
            if (user.getEmail() != null) {
                user.setEmail(user.getEmail().trim());
                if (user.getEmail().length() > 100) {
                    throw new BusinessException("邮箱不能超过100字符");
                }
            }
            if (user.getRole() != null) {
                user.setRole(user.getRole().trim());
                if (!"ADMIN".equals(user.getRole()) && !"ANALYST".equals(user.getRole()) && !"USER".equals(user.getRole())) {
                    throw new BusinessException("角色不合法");
                }
                roleChanged = !user.getRole().equals(db.getRole());
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                user.setPassword(db.getPassword());
            } else {
                user.setPassword(user.getPassword().trim());
                if (user.getPassword().length() > 100) {
                    throw new BusinessException("密码不能超过100字符");
                }
                passwordChanged = !user.getPassword().equals(db.getPassword());
            }
            userMapper.updateById(user);
            if (passwordChanged || roleChanged) {
                redisTemplate.delete("fraud:token:" + user.getId());
            }
        }
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("用户状态不合法");
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setStatus(status);
        userMapper.updateById(user);
        if (status == 0) {
            redisTemplate.delete("fraud:token:" + id);
        }
    }

    public void deleteById(Long id) {
        if (id == 1L) {
            throw new BusinessException("默认管理员不可删除");
        }
        userMapper.deleteById(id);
        redisTemplate.delete("fraud:token:" + id);
    }

    private User safeUser(User user) {
        if (user == null) {
            return null;
        }
        user.setPassword(null);
        return user;
    }
}
