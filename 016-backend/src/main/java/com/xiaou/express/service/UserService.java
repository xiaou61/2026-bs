package com.xiaou.express.service;

import com.xiaou.express.common.BusinessException;
import com.xiaou.express.dto.PasswordUpdateRequest;
import com.xiaou.express.dto.UserProfileUpdateRequest;
import com.xiaou.express.entity.User;
import com.xiaou.express.mapper.UserMapper;
import cn.hutool.crypto.digest.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Transactional
    public void updateProfile(Long userId, UserProfileUpdateRequest request) {
        User user = getUserById(userId);
        if (StringUtils.hasText(request.getUsername())) {
            user.setUsername(request.getUsername().trim());
        }
        if (StringUtils.hasText(request.getDormitoryBuilding())) {
            user.setDormitoryBuilding(request.getDormitoryBuilding().trim());
        }
        if (StringUtils.hasText(request.getDormitoryRoom())) {
            user.setDormitoryRoom(request.getDormitoryRoom().trim());
        }
        userMapper.updateById(user);
    }

    @Transactional
    public void updatePassword(Long userId, PasswordUpdateRequest request) {
        User user = getUserById(userId);
        if (!StringUtils.hasText(request.getOldPassword()) || !StringUtils.hasText(request.getNewPassword())) {
            throw new BusinessException("密码不能为空");
        }
        if (!BCrypt.checkpw(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        if (request.getNewPassword().length() < 6) {
            throw new BusinessException("新密码长度不能少于6位");
        }
        user.setPassword(BCrypt.hashpw(request.getNewPassword()));
        userMapper.updateById(user);
    }
}

