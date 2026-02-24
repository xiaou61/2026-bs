package com.hrm.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.common.BusinessException;
import com.hrm.entity.User;
import com.hrm.mapper.UserMapper;
import com.hrm.utils.JwtUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }
        String md5Pwd = DigestUtil.md5Hex(password);
        if (!md5Pwd.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        String token = JwtUtils.generateToken(user.getId(), user.getUsername());
        stringRedisTemplate.opsForValue().set("token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        user.setPassword(null);
        result.put("user", user);
        return result;
    }

    public void logout(Long userId) {
        stringRedisTemplate.delete("token:" + userId);
    }

    public User getById(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public PageInfo<User> getList(String username, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(userMapper.selectList(username, role));
    }

    public void add(User user) {
        if (userMapper.countByUsername(user.getUsername()) > 0) {
            throw new BusinessException("用户名已存在");
        }
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        user.setStatus(1);
        userMapper.insert(user);
    }

    public void update(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        userMapper.update(user);
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    public void resetPassword(Long id, String newPassword) {
        User user = new User();
        user.setId(id);
        user.setPassword(DigestUtil.md5Hex(newPassword));
        userMapper.update(user);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (!DigestUtil.md5Hex(oldPassword).equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(DigestUtil.md5Hex(newPassword));
        userMapper.update(updateUser);
    }
}
