package com.xiaou.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.User;
import com.xiaou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Page<User> getList(Integer pageNum, Integer pageSize, String keyword, Integer role) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.and(w -> w.like(User::getUsername, keyword)
                    .or().like(User::getNickname, keyword)
                    .or().like(User::getPhone, keyword));
        }
        if (role != null) {
            wrapper.eq(User::getRole, role);
        }
        wrapper.orderByDesc(User::getCreateTime);
        Page<User> result = userMapper.selectPage(page, wrapper);
        result.getRecords().forEach(u -> u.setPassword(null));
        return result;
    }

    public User getById(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void save(User user) {
        if (user.getId() == null) {
            User exist = userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getUsername, user.getUsername()));
            if (exist != null) {
                throw new BusinessException("用户名已存在");
            }
            if (StrUtil.isBlank(user.getPassword())) {
                user.setPassword(DigestUtil.md5Hex("123456"));
            } else {
                user.setPassword(DigestUtil.md5Hex(user.getPassword()));
            }
            userMapper.insert(user);
        } else {
            User old = userMapper.selectById(user.getId());
            if (StrUtil.isNotBlank(user.getPassword())) {
                user.setPassword(DigestUtil.md5Hex(user.getPassword()));
            } else {
                user.setPassword(old.getPassword());
            }
            userMapper.updateById(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    public void updateStatus(Long id, Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userMapper.updateById(user);
    }

    public void resetPassword(Long id) {
        User user = new User();
        user.setId(id);
        user.setPassword(DigestUtil.md5Hex("123456"));
        userMapper.updateById(user);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (!user.getPassword().equals(DigestUtil.md5Hex(oldPassword))) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(DigestUtil.md5Hex(newPassword));
        userMapper.updateById(user);
    }

    public void updateInfo(Long userId, User user) {
        user.setId(userId);
        user.setPassword(null);
        user.setRole(null);
        user.setStatus(null);
        userMapper.updateById(user);
    }

    public List<User> getJudgeList() {
        return userMapper.selectList(new LambdaQueryWrapper<User>()
                .eq(User::getRole, 1)
                .eq(User::getStatus, 1)
                .select(User::getId, User::getNickname, User::getUsername));
    }
}
