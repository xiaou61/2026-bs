package com.folksong.platform.service;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.dto.LoginDTO;
import com.folksong.platform.dto.RegisterDTO;
import com.folksong.platform.dto.UserUpdateDTO;
import com.folksong.platform.vo.LoginVO;
import com.folksong.platform.vo.UserVO;

public interface UserService {
    LoginVO login(LoginDTO dto);
    void register(RegisterDTO dto);
    UserVO getCurrentUser(Long userId);
    void updateUser(Long userId, UserUpdateDTO dto);
    void changePassword(Long userId, String oldPassword, String newPassword);
    PageResult<UserVO> getUserList(Integer pageNum, Integer pageSize, Integer status);
    void updateUserStatus(Long userId, Integer status);
    void deleteUser(Long userId);
}
