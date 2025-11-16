package com.xiaou.studyroom.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.studyroom.entity.CreditRecord;
import com.xiaou.studyroom.entity.User;
import com.xiaou.studyroom.mapper.CreditRecordMapper;
import com.xiaou.studyroom.mapper.UserMapper;
import com.xiaou.studyroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private CreditRecordMapper creditRecordMapper;

    @Override
    public User login(String username, String password) {
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return null;
        }

        String encryptedPassword = SecureUtil.md5(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username)
                   .eq("password", encryptedPassword)
                   .eq("status", 1);

        return getOne(queryWrapper);
    }

    @Override
    @Transactional
    public boolean register(User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return false;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        if (count(queryWrapper) > 0) {
            return false;
        }

        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setCreditScore(100);
        user.setStatus(1);

        return save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return getOne(queryWrapper);
    }

    @Override
    @Transactional
    public boolean updateUserCredit(Long userId, Integer scoreChange, String reason) {
        User user = getById(userId);
        if (user == null) {
            return false;
        }

        user.setCreditScore(user.getCreditScore() + scoreChange);
        if (user.getCreditScore() < 0) {
            user.setCreditScore(0);
        }

        boolean result = updateById(user);

        if (result) {
            CreditRecord record = new CreditRecord();
            record.setUserId(userId);
            record.setScoreChange(scoreChange);
            record.setChangeReason(reason);
            record.setRelatedType("SYSTEM");
            creditRecordMapper.insert(record);
        }

        return result;
    }

    @Override
    public Page<User> getUserPage(int current, int size, String keyword) {
        Page<User> page = new Page<>(current, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                .like("real_name", keyword)
                .or()
                .like("username", keyword)
                .or()
                .like("department", keyword)
            );
        }

        return page(page, queryWrapper);
    }
}