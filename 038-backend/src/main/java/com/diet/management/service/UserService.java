package com.diet.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diet.management.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);
    
    /**
     * 用户注册
     */
    boolean register(User user);
    
    /**
     * 用户登录
     */
    String login(String username, String password);
    
    /**
     * 更新用户信息
     */
    boolean updateUserInfo(User user);
}
