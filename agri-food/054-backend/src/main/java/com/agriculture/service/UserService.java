package com.agriculture.service;

import com.agriculture.common.BusinessException;
import com.agriculture.dto.LoginDTO;
import com.agriculture.entity.User;
import com.agriculture.mapper.UserMapper;
import com.agriculture.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 64;
    private static final int MAX_PAGE_SIZE = 500;

    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(JwtUtils jwtUtils, BCryptPasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginDTO dto) {
        if (dto == null || !StringUtils.hasText(dto.getUsername()) || !StringUtils.hasText(dto.getPassword())) {
            throw new BusinessException("用户名或密码不能为空");
        }
        User user = this.lambdaQuery()
                .eq(User::getUsername, dto.getUsername())
                .one();
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        return jwtUtils.generateToken(user.getId().toString());
    }

    public User getUserInfo(Long userId) {
        User user = this.getById(userId);
        if (user == null) {
            return null;
        }
        User safe = new User();
        safe.setId(user.getId());
        safe.setUsername(user.getUsername());
        safe.setRealName(user.getRealName());
        safe.setPhone(user.getPhone());
        safe.setAvatar(user.getAvatar());
        safe.setRole(user.getRole());
        safe.setStatus(user.getStatus());
        safe.setCreateTime(user.getCreateTime());
        return safe;
    }

    @Transactional
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (!StringUtils.hasText(newPassword)) {
            throw new BusinessException(400, "新密码不能为空");
        }
        validatePassword(newPassword);
        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException(400, "原密码错误");
        }
        if (Objects.equals(oldPassword, newPassword)) {
            throw new BusinessException(400, "新旧密码不能一致");
        }
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(passwordEncoder.encode(newPassword));
        this.updateById(updateUser);
    }

    public Page<User> getUserPage(Integer pageNum, Integer pageSize, String username, String role) {
        int size = pageSize == null ? 10 : Math.min(pageSize, MAX_PAGE_SIZE);
        Page<User> page = new Page<>(pageNum == null ? 1 : pageNum, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            wrapper.like(User::getUsername, username);
        }
        if (StringUtils.hasText(role)) {
            wrapper.eq(User::getRole, role);
        }
        Page<User> result = this.page(page, wrapper);
        result.getRecords().forEach(this::clearPassword);
        return result;
    }

    @Override
    @Transactional
    public boolean save(User user) {
        encodePasswordIfNeeded(user);
        return super.save(user);
    }

    @Override
    @Transactional
    public boolean updateById(User user) {
        encodePasswordIfNeeded(user);
        return super.updateById(user);
    }

    private void encodePasswordIfNeeded(User user) {
        if (user != null && StringUtils.hasText(user.getPassword())) {
            validatePassword(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
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
