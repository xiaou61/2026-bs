package com.rental.service;

import com.rental.entity.User;

import java.util.Map;

public interface AuthService {

    /**
     * 用户注册
     */
    void register(String username, String password, String role, String phone);

    /**
     * 用户登录
     */
    Map<String, Object> login(String username, String password);

    /**
     * 获取当前用户信息
     */
    User getUserInfo(Long userId);

    /**
     * 修改密码
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 更新用户信息
     */
    void updateUserInfo(Long userId, User user);
}
