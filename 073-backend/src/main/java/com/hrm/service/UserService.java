package com.hrm.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.common.BusinessException;
import com.hrm.entity.User;
import com.hrm.mapper.UserMapper;
import com.hrm.utils.JwtUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RuntimeStoreService runtimeStoreService;

    public Map<String, Object> login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
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
        String token = jwtUtils.generateToken(user.getId(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public void logout(String token) {
        runtimeStoreService.invalidateToken(token);
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
        if (user.getUsername() == null || user.getUsername().isBlank() || user.getPassword() == null || user.getPassword().isBlank()) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
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
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if ("admin".equals(user.getRole())) {
            throw new BusinessException(400, "不能删除管理员账号");
        }
        userMapper.deleteById(id);
    }

    public void resetPassword(Long id, String newPassword) {
        User user = new User();
        user.setId(id);
        user.setPassword(DigestUtil.md5Hex(newPassword));
        userMapper.update(user);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new BusinessException(400, "新密码至少6位");
        }
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
