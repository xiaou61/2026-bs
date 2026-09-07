package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.User;
import com.xiaou.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User getUserByUsername(String username) {
        return this.getOne(new QueryWrapper<User>().eq("username", username));
    }

    public User register(User user) {
        user.setRole("VOLUNTEER");
        user.setTotalPoints(0);
        user.setAvailablePoints(0);
        user.setVolunteerHours(0.0);
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return user;
    }

    public void updatePoints(Long userId, Integer points) {
        User user = this.getById(userId);
        if (user != null) {
            user.setTotalPoints(user.getTotalPoints() + points);
            user.setAvailablePoints(user.getAvailablePoints() + points);
            user.setUpdateTime(LocalDateTime.now());
            this.updateById(user);
        }
    }

    public void updateVolunteerHours(Long userId, Double hours) {
        User user = this.getById(userId);
        if (user != null) {
            user.setVolunteerHours(user.getVolunteerHours() + hours);
            user.setUpdateTime(LocalDateTime.now());
            this.updateById(user);
        }
    }
}

