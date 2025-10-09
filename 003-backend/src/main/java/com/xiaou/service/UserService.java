package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);
    void register(User user);
    void changePassword(Long userId, String oldPassword, String newPassword);
}

