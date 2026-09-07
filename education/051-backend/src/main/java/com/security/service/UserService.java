package com.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.security.dto.LoginDTO;
import com.security.dto.UserUpdateDTO;
import com.security.entity.User;
import com.security.vo.LoginVO;
import com.security.vo.UserStatsVO;
import com.security.vo.UserVO;

public interface UserService extends IService<User> {
    LoginVO login(LoginDTO dto);
    UserVO getUserInfo(Long userId);
    void updateUserInfo(Long userId, UserUpdateDTO dto);
    UserStatsVO getUserStats(Long userId);
    void addPoints(Long userId, Integer points);
}
