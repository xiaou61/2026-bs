package com.xiaou.campusvideo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusvideo.entity.User;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.service.UserService;
import com.xiaou.campusvideo.service.VideoService;
import com.xiaou.campusvideo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/video")
    public Result<IPage<Video>> searchVideos(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        Page<Video> videoPage = new Page<>(page, size);
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w
                .like(Video::getTitle, keyword)
                .or()
                .like(Video::getDescription, keyword))
               .eq(Video::getStatus, 1)
               .orderByDesc(Video::getHeatScore);
        
        IPage<Video> result = videoService.page(videoPage, wrapper);
        return Result.success(result);
    }
    
    @GetMapping("/user")
    public Result<IPage<User>> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        Page<User> userPage = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w
                .like(User::getNickname, keyword)
                .or()
                .like(User::getUsername, keyword))
               .eq(User::getStatus, 1)
               .orderByDesc(User::getFansCount);
        
        IPage<User> result = userService.page(userPage, wrapper);
        return Result.success(result);
    }
}

