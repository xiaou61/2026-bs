package com.movie.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.common.BusinessException;
import com.movie.entity.User;
import com.movie.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已禁用");
        }
        return user;
    }

    public void register(User user) {
        User exist = userMapper.selectByUsername(user.getUsername());
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole("user");
        user.setStatus(1);
        userMapper.insert(user);
    }

    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    public PageInfo<User> getPage(Integer pageNum, Integer pageSize, String username) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(userMapper.selectPage(username));
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void updateStatus(Long id, Integer status) {
        userMapper.updateStatus(id, status);
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userMapper.selectById(id);
        if (!oldPassword.equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        User update = new User();
        update.setId(id);
        update.setPassword(newPassword);
        userMapper.update(update);
    }
}
