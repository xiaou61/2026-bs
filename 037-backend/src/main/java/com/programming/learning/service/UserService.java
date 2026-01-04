package com.programming.learning.service;

import com.programming.learning.entity.User;
import com.programming.learning.enums.UserRole;
import com.programming.learning.enums.UserStatus;
import com.programming.learning.exception.BusinessException;
import com.programming.learning.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Service
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据ID获取用户
     */
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    /**
     * 根据openId获取用户
     */
    public User getUserByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    /**
     * 创建用户
     */
    @Transactional(rollbackFor = Exception.class)
    public User createUser(String openId, String nickname, String avatar) {
        // 检查用户是否已存在
        User existUser = userMapper.selectByOpenId(openId);
        if (existUser != null) {
            return existUser;
        }

        // 创建新用户
        User user = new User();
        user.setOpenId(openId);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.NORMAL);
        user.setScore(0);
        user.setLevel(1);

        userMapper.insert(user);
        log.info("创建用户成功: {}", user.getId());
        return user;
    }

    /**
     * 更新用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        int rows = userMapper.update(user);
        if (rows == 0) {
            throw new BusinessException("更新用户失败");
        }
    }

    /**
     * 更新最后登录信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateLastLogin(Long userId, String ip) {
        userMapper.updateLastLogin(userId, ip);
    }

    /**
     * 更新用户积分
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateScore(Long userId, Integer score) {
        userMapper.updateScore(userId, score);
        
        // 根据积分更新等级
        updateLevel(userId, score);
    }

    /**
     * 根据积分更新等级
     */
    private void updateLevel(Long userId, Integer score) {
        int level = 1;
        if (score >= 5000) {
            level = 5;
        } else if (score >= 1500) {
            level = 4;
        } else if (score >= 500) {
            level = 3;
        } else if (score >= 100) {
            level = 2;
        }
        
        userMapper.updateLevel(userId, level);
    }
}
