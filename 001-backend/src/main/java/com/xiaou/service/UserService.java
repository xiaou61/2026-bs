package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.User;

/**
 * 用户Service接口
 * @author xiaou
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
    User getUserByUsername(String username);
    
    /**
     * 分页查询用户列表
     */
    Page<User> getUserPage(int pageNum, int pageSize, String keyword);
    
    /**
     * 修改密码
     */
    void changePassword(Long userId, String oldPassword, String newPassword);
}

