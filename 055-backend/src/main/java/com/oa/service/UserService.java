package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.common.BusinessException;
import com.oa.entity.Department;
import com.oa.entity.User;
import com.oa.mapper.DepartmentMapper;
import com.oa.mapper.UserMapper;
import com.oa.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (user == null || !user.getPassword().equals(password)) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }
        String token = JwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        user.setPassword(null);
        if (user.getDeptId() != null) {
            Department dept = departmentMapper.selectById(user.getDeptId());
            if (dept != null) user.setDeptName(dept.getName());
        }
        result.put("user", user);
        return result;
    }

    public User getInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPassword(null);
            if (user.getDeptId() != null) {
                Department dept = departmentMapper.selectById(user.getDeptId());
                if (dept != null) user.setDeptName(dept.getName());
            }
        }
        return user;
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (!user.getPassword().equals(oldPassword)) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(newPassword);
        userMapper.updateById(user);
    }

    public void updateProfile(Long userId, User profile) {
        User user = userMapper.selectById(userId);
        user.setRealName(profile.getRealName());
        user.setGender(profile.getGender());
        user.setPhone(profile.getPhone());
        user.setEmail(profile.getEmail());
        userMapper.updateById(user);
    }

    public Page<User> getList(Integer pageNum, Integer pageSize, String keyword, Long deptId) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(User::getUsername, keyword).or().like(User::getRealName, keyword).or().like(User::getPhone, keyword));
        }
        if (deptId != null) {
            wrapper.eq(User::getDeptId, deptId);
        }
        wrapper.orderByDesc(User::getCreateTime);
        Page<User> result = userMapper.selectPage(page, wrapper);
        result.getRecords().forEach(u -> {
            u.setPassword(null);
            if (u.getDeptId() != null) {
                Department dept = departmentMapper.selectById(u.getDeptId());
                if (dept != null) u.setDeptName(dept.getName());
            }
        });
        return result;
    }

    public List<User> getAll() {
        List<User> list = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getStatus, 1));
        list.forEach(u -> u.setPassword(null));
        return list;
    }

    public void add(User user) {
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        userMapper.insert(user);
    }

    public void update(User user) {
        user.setPassword(null);
        userMapper.updateById(user);
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }
}
