package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);
    User wxLogin(String openid);
    IPage<User> pageUsers(Integer page, Integer size, Integer role, String keyword);
    boolean updatePassword(Long userId, String oldPassword, String newPassword);
}
