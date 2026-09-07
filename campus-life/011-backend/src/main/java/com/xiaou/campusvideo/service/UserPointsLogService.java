package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.User;
import com.xiaou.campusvideo.entity.UserPointsLog;
import com.xiaou.campusvideo.mapper.UserPointsLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserPointsLogService extends ServiceImpl<UserPointsLogMapper, UserPointsLog> {
    
    @Autowired
    private UserService userService;
    
    @Transactional
    public void addPoints(Long userId, Integer points, String changeType, String reason, Long relatedId) {
        User user = userService.getById(userId);
        if (user == null) {
            return;
        }
        
        UserPointsLog log = new UserPointsLog();
        log.setUserId(userId);
        log.setChangeType(changeType);
        log.setChangePoints(points);
        log.setBeforePoints(user.getPoints());
        log.setAfterPoints(user.getPoints() + points);
        log.setReason(reason);
        log.setRelatedId(relatedId);
        this.save(log);
        
        userService.updatePoints(userId, points);
    }
    
    public List<UserPointsLog> getPointsLog(Long userId) {
        LambdaQueryWrapper<UserPointsLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPointsLog::getUserId, userId)
               .orderByDesc(UserPointsLog::getCreateTime);
        return this.list(wrapper);
    }
}

