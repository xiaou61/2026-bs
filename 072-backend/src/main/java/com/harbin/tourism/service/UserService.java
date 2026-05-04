package com.harbin.tourism.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.BusinessException;
import com.harbin.tourism.entity.User;
import com.harbin.tourism.mapper.UserMapper;
import com.harbin.tourism.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    public Map<String, Object> login(String username, String password) {
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public void register(User user) {
        if (user == null || StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
        if (user.getPassword().length() < 6) {
            throw new BusinessException(400, "密码至少6位");
        }
        User existing = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        if (existing != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole("user");
        user.setBalance(BigDecimal.ZERO);
        user.setStatus(1);
        userMapper.insert(user);
    }

    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    public void updateProfile(Long userId, User user) {
        User update = new User();
        update.setId(userId);
        update.setNickname(user.getNickname());
        update.setAvatar(user.getAvatar());
        update.setPhone(user.getPhone());
        update.setEmail(user.getEmail());
        userMapper.updateById(update);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        if (StrUtil.isBlank(oldPassword) || StrUtil.isBlank(newPassword)) {
            throw new BusinessException(400, "原密码和新密码不能为空");
        }
        if (newPassword.length() < 6) {
            throw new BusinessException(400, "新密码至少6位");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if (!user.getPassword().equals(oldPassword)) {
            throw new BusinessException(400, "原密码错误");
        }
        user.setPassword(newPassword);
        userMapper.updateById(user);
    }

    public Page<User> page(Integer pageNum, Integer pageSize, String keyword) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(User::getUsername, keyword)
                    .or().like(User::getNickname, keyword);
        }
        wrapper.orderByDesc(User::getCreatedAt);
        return userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(400, "状态值不合法");
        }
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userMapper.updateById(user);
    }

    public void recharge(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(400, "充值金额必须大于0");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        user.setBalance(user.getBalance().add(amount));
        userMapper.updateById(user);
    }

    public void deduct(Long userId, BigDecimal amount) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if (user.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("余额不足");
        }
        user.setBalance(user.getBalance().subtract(amount));
        userMapper.updateById(user);
    }

    public long count() {
        return userMapper.selectCount(null);
    }

    public void delete(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if ("admin".equals(user.getRole())) {
            throw new BusinessException(400, "不能删除管理员账号");
        }
        userMapper.deleteById(id);
    }
}
