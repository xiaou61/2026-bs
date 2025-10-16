package com.xiaou.campusclub.service;

import com.xiaou.campusclub.dto.LoginRequest;
import com.xiaou.campusclub.dto.RegisterRequest;
import com.xiaou.campusclub.entity.User;
import com.xiaou.campusclub.vo.LoginVO;
import com.xiaou.campusclub.vo.UserInfoVO;

import java.util.List;

public interface UserService {
    LoginVO register(RegisterRequest request);
    LoginVO login(LoginRequest request);
    UserInfoVO getUserInfo(Long userId);
    void updateProfile(Long userId, User user);
    void addPoints(Long userId, Integer points, String type, String description);
    List<String> getUserInterests(Long userId);
    void addUserInterest(Long userId, Long interestId);
    void removeUserInterest(Long userId, Long interestId);
}

