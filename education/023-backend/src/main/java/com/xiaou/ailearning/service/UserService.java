package com.xiaou.ailearning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.ailearning.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户登录
     */
    String login(String username, String password);
    
    /**
     * 用户注册
     */
    void register(User user);
    
    /**
     * 根据用户名查询用户
     */
    User findByUsername(String username);
    
    /**
     * 更新用户学习偏好
     */
    void updateLearningPreferences(Long userId, User preferences);
}