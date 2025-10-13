package com.xiaou.campusvideo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusvideo.entity.User;
import com.xiaou.campusvideo.entity.UserFollow;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.service.UserFollowService;
import com.xiaou.campusvideo.service.UserPointsLogService;
import com.xiaou.campusvideo.service.UserService;
import com.xiaou.campusvideo.service.VideoService;
import com.xiaou.campusvideo.util.FileUtil;
import com.xiaou.campusvideo.util.Result;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private UserFollowService userFollowService;
    
    @Autowired
    private UserPointsLogService userPointsLogService;
    
    @Autowired
    private FileUtil fileUtil;
    
    @GetMapping("/{id}")
    public Result<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        Long currentUserId = UserHolder.getUserId();
        if (currentUserId != null && !currentUserId.equals(id)) {
            user.setIsFollow(userFollowService.isFollow(currentUserId, id));
        }
        
        return Result.success(user);
    }
    
    @PutMapping("/profile")
    public Result<?> updateProfile(@RequestBody User user) {
        Long userId = UserHolder.getUserId();
        user.setId(userId);
        userService.updateById(user);
        return Result.success("更新成功");
    }
    
    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = fileUtil.uploadAvatar(file);
            
            Long userId = UserHolder.getUserId();
            User user = userService.getById(userId);
            user.setAvatar(avatarUrl);
            userService.updateById(user);
            
            return Result.success(avatarUrl);
        } catch (Exception e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }
    
    @GetMapping("/{id}/videos")
    public Result<IPage<Video>> getUserVideos(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Page<Video> videoPage = new Page<>(page, size);
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getUserId, id)
               .eq(Video::getStatus, 1)
               .orderByDesc(Video::getPublishTime);
        
        IPage<Video> result = videoService.page(videoPage, wrapper);
        return Result.success(result);
    }
    
    @PostMapping("/follow/{userId}")
    public Result<?> follow(@PathVariable Long userId) {
        try {
            Long currentUserId = UserHolder.getUserId();
            userFollowService.follow(currentUserId, userId);
            return Result.success("关注成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/follow/{userId}")
    public Result<?> unfollow(@PathVariable Long userId) {
        Long currentUserId = UserHolder.getUserId();
        userFollowService.unfollow(currentUserId, userId);
        return Result.success("取消关注成功");
    }
    
    @GetMapping("/{id}/followers")
    public Result<List<User>> getFollowers(@PathVariable Long id) {
        List<UserFollow> follows = userFollowService.getFollowersList(id);
        List<User> users = new ArrayList<>();
        for (UserFollow follow : follows) {
            users.add(userService.getById(follow.getUserId()));
        }
        return Result.success(users);
    }
    
    @GetMapping("/{id}/following")
    public Result<List<User>> getFollowing(@PathVariable Long id) {
        List<UserFollow> follows = userFollowService.getFollowingList(id);
        List<User> users = new ArrayList<>();
        for (UserFollow follow : follows) {
            users.add(userService.getById(follow.getFollowUserId()));
        }
        return Result.success(users);
    }
    
    @GetMapping("/points/log")
    public Result<?> getPointsLog() {
        Long userId = UserHolder.getUserId();
        return Result.success(userPointsLogService.getPointsLog(userId));
    }
    
    @GetMapping("/{id}/likes")
    public Result<IPage<Video>> getUserLikes(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        return Result.success(videoService.getUserLikedVideos(id, page, size));
    }
    
    @GetMapping("/{id}/collects")
    public Result<IPage<Video>> getUserCollects(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        return Result.success(videoService.getUserCollectedVideos(id, page, size));
    }
}


