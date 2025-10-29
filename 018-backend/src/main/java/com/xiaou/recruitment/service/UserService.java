package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.User;
import com.xiaou.recruitment.mapper.UserMapper;
import com.xiaou.recruitment.utils.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User register(User user) {
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        save(user);
        return user;
    }

    public User login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = getOne(wrapper);
        if (user != null && PasswordUtil.verify(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User getUserById(Long id) {
        return getById(id);
    }

    public boolean updateUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        }
        return updateById(user);
    }
}
