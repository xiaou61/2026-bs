package com.xiaou.pet.service;

import com.xiaou.pet.entity.User;
import com.xiaou.pet.common.Result;

public interface UserService {
    Result<User> login(User user);
    Result<User> register(User user);
    Result<User> getUserInfo(Long userId);
    Result<User> updateUserInfo(User user);
}
