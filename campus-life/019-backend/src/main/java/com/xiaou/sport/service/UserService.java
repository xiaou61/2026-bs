package com.xiaou.sport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.sport.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);

    boolean register(User user);
}
