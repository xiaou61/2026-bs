package com.xiaou.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.CreditLog;
import com.xiaou.entity.User;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.CreditLogMapper;
import com.xiaou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private CreditLogMapper creditLogMapper;

    public User register(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentNo, user.getStudentNo());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("学号已存在");
        }
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setCreditScore(100);
        user.setRole("USER");
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
        user.setPassword(null);
        return user;
    }

    public User login(String studentNo, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentNo, studentNo);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new BusinessException("学号或密码错误");
        }
        if (!SecureUtil.md5(password).equals(user.getPassword())) {
            throw new BusinessException("学号或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        if (user.getBanUntil() != null && user.getBanUntil().isAfter(LocalDateTime.now())) {
            throw new BusinessException("账号已被临时禁用，禁用至：" + user.getBanUntil());
        }
        user.setPassword(null);
        return user;
    }

    public User getById(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (!SecureUtil.md5(oldPassword).equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(SecureUtil.md5(newPassword));
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    public void updateCreditScore(Long userId, Integer changeScore, String reason, Long relatedId) {
        User user = userMapper.selectById(userId);
        Integer beforeScore = user.getCreditScore();
        Integer afterScore = beforeScore + changeScore;
        user.setCreditScore(afterScore);
        user.setUpdateTime(LocalDateTime.now());
        
        if (afterScore < 40 && afterScore >= 0) {
            user.setBanUntil(LocalDateTime.now().plusDays(3));
            user.setStatus(2);
        } else if (afterScore < 0) {
            user.setBanUntil(LocalDateTime.now().plusDays(7));
            user.setStatus(2);
        } else if (afterScore >= 40) {
            user.setBanUntil(null);
            if (user.getStatus() == 2) {
                user.setStatus(1);
            }
        }
        
        userMapper.updateById(user);
        
        CreditLog creditLog = new CreditLog();
        creditLog.setUserId(userId);
        creditLog.setChangeType(changeScore > 0 ? "ADD" : "DEDUCT");
        creditLog.setChangeScore(changeScore);
        creditLog.setBeforeScore(beforeScore);
        creditLog.setAfterScore(afterScore);
        creditLog.setRelatedId(relatedId);
        creditLog.setReason(reason);
        creditLog.setCreateTime(LocalDateTime.now());
        creditLogMapper.insert(creditLog);
    }
}

