package com.xiaou.service;

import com.xiaou.dto.UserLoginDTO;
import com.xiaou.dto.UserRegisterDTO;
import com.xiaou.vo.UserInfoVO;
import com.xiaou.vo.UserLoginVO;

public interface UserService {

    String register(UserRegisterDTO registerDTO);

    UserLoginVO login(UserLoginDTO loginDTO);

    UserInfoVO getUserInfo(Long userId);

    void updateUser(Long userId, UserRegisterDTO updateDTO);

    UserInfoVO getUserProfile(Long userId, Long profileId);
}