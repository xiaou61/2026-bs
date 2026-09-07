package com.xiaou.ailearning.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.ailearning.entity.User;
import com.xiaou.ailearning.mapper.UserMapper;
import com.xiaou.ailearning.service.UserService;
import com.xiaou.ailearning.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public String login(String username, String password) {
        // 查询用户
        User user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }

        // 验证密码
        String encryptedPassword = DigestUtil.md5Hex(password);
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 生成token
        return jwtUtil.generateToken(username, user.getId());
    }

    @Override
    public void register(User user) {
        // 检查用户名是否已存在
        if (findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 密码加密
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        
        // 设置默认值
        if (user.getLearningStyle() == null) {
            user.setLearningStyle(1);
        }
        if (user.getCognitiveLevel() == null) {
            user.setCognitiveLevel(0.5);
        }
        if (user.getDifficultyPreference() == null) {
            user.setDifficultyPreference(2);
        }

        save(user);
    }

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return getOne(wrapper);
    }

    @Override
    public void updateLearningPreferences(Long userId, User preferences) {
        User user = getById(userId);
        if (user != null) {
            user.setLearningStyle(preferences.getLearningStyle());
            user.setCognitiveLevel(preferences.getCognitiveLevel());
            user.setDifficultyPreference(preferences.getDifficultyPreference());
            user.setStudyTimePreference(preferences.getStudyTimePreference());
            updateById(user);
        }
    }
}