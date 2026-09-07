package com.xiaou.herbal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.herbal.dto.LoginRequest;
import com.xiaou.herbal.dto.LoginResponse;
import com.xiaou.herbal.dto.RegisterRequest;
import com.xiaou.herbal.entity.User;

public interface UserService extends IService<User> {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    User getUserInfo(Long userId);

    void updateUserInfo(Long userId, User user);

    User findByUsername(String username);
}
