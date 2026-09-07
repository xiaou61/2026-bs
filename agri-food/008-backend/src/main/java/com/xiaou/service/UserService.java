package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.LoginDTO;
import com.xiaou.dto.RegisterDTO;
import com.xiaou.dto.UserUpdateDTO;
import com.xiaou.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {
    
    Map<String, Object> login(LoginDTO dto);
    
    void register(RegisterDTO dto);
    
    User getUserInfo(Long userId);
    
    void updateUserInfo(Long userId, UserUpdateDTO dto);
}

