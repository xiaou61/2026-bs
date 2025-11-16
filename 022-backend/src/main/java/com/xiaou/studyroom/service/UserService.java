package com.xiaou.studyroom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.studyroom.entity.User;

public interface UserService extends IService<User> {

    User login(String username, String password);

    boolean register(User user);

    User getUserByUsername(String username);

    boolean updateUserCredit(Long userId, Integer scoreChange, String reason);

    Page<User> getUserPage(int current, int size, String keyword);
}