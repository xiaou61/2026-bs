package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.User;
import com.xiaou.campusvideo.entity.UserFollow;
import com.xiaou.campusvideo.mapper.UserFollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserFollowService extends ServiceImpl<UserFollowMapper, UserFollow> {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Transactional
    public void follow(Long userId, Long followUserId) {
        if (userId.equals(followUserId)) {
            throw new RuntimeException("不能关注自己");
        }
        
        if (isFollow(userId, followUserId)) {
            return;
        }
        
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setFollowUserId(followUserId);
        this.save(userFollow);
        
        User user = userService.getById(userId);
        user.setFollowCount(user.getFollowCount() + 1);
        userService.updateById(user);
        
        User followUser = userService.getById(followUserId);
        followUser.setFansCount(followUser.getFansCount() + 1);
        userService.updateById(followUser);
        
        notificationService.sendNotification(
            followUserId,
            userId,
            "FOLLOW",
            "关注了你",
            null
        );
    }
    
    @Transactional
    public void unfollow(Long userId, Long followUserId) {
        LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollow::getUserId, userId)
               .eq(UserFollow::getFollowUserId, followUserId);
        this.remove(wrapper);
        
        User user = userService.getById(userId);
        if (user.getFollowCount() > 0) {
            user.setFollowCount(user.getFollowCount() - 1);
            userService.updateById(user);
        }
        
        User followUser = userService.getById(followUserId);
        if (followUser.getFansCount() > 0) {
            followUser.setFansCount(followUser.getFansCount() - 1);
            userService.updateById(followUser);
        }
    }
    
    public boolean isFollow(Long userId, Long followUserId) {
        LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollow::getUserId, userId)
               .eq(UserFollow::getFollowUserId, followUserId);
        return this.count(wrapper) > 0;
    }
    
    public List<UserFollow> getFollowingList(Long userId) {
        LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollow::getUserId, userId);
        return this.list(wrapper);
    }
    
    public List<UserFollow> getFollowersList(Long userId) {
        LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollow::getFollowUserId, userId);
        return this.list(wrapper);
    }
}

