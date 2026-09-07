package com.xiaou.campusvideo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusvideo.entity.*;
import com.xiaou.campusvideo.service.*;
import com.xiaou.campusvideo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private VideoReportService videoReportService;
    
    @GetMapping("/stats/overview")
    public Result<Map<String, Object>> getOverview() {
        Map<String, Object> data = new HashMap<>();
        
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getStatus, 1);
        long userCount = userService.count(userWrapper);
        
        LambdaQueryWrapper<Video> videoWrapper = new LambdaQueryWrapper<>();
        videoWrapper.eq(Video::getStatus, 1);
        long videoCount = videoService.count(videoWrapper);
        
        long topicCount = topicService.count();
        long commentCount = commentService.count();
        
        data.put("userCount", userCount);
        data.put("videoCount", videoCount);
        data.put("topicCount", topicCount);
        data.put("commentCount", commentCount);
        
        return Result.success(data);
    }
    
    @GetMapping("/user/list")
    public Result<IPage<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String keyword) {
        
        Page<User> userPage = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(User::getNickname, keyword)
                   .or()
                   .like(User::getUsername, keyword);
        }
        
        wrapper.orderByDesc(User::getCreateTime);
        IPage<User> result = userService.page(userPage, wrapper);
        return Result.success(result);
    }
    
    @PutMapping("/user/{id}/status")
    public Result<?> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        User user = userService.getById(id);
        user.setStatus(status);
        userService.updateById(user);
        return Result.success("更新成功");
    }
    
    @GetMapping("/video/list")
    public Result<IPage<Video>> getVideoList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) Integer status) {
        
        Page<Video> videoPage = new Page<>(page, size);
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Video::getStatus, status);
        }
        
        wrapper.orderByDesc(Video::getCreateTime);
        IPage<Video> result = videoService.page(videoPage, wrapper);
        return Result.success(result);
    }
    
    @PutMapping("/video/{id}/audit")
    public Result<?> auditVideo(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Integer status = (Integer) params.get("status");
        String auditReason = (String) params.get("auditReason");
        
        Video video = videoService.getById(id);
        video.setStatus(status);
        video.setAuditReason(auditReason);
        videoService.updateById(video);
        
        return Result.success("审核成功");
    }
    
    @DeleteMapping("/video/{id}")
    public Result<?> deleteVideo(@PathVariable Long id) {
        Video video = videoService.getById(id);
        video.setStatus(3);
        videoService.updateById(video);
        return Result.success("删除成功");
    }
    
    @GetMapping("/topic/list")
    public Result<IPage<Topic>> getTopicList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        Page<Topic> topicPage = new Page<>(page, size);
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Topic::getCreateTime);
        
        IPage<Topic> result = topicService.page(topicPage, wrapper);
        return Result.success(result);
    }
    
    @PostMapping("/topic")
    public Result<?> createTopic(@RequestBody Topic topic) {
        topicService.save(topic);
        return Result.success("创建成功");
    }
    
    @PutMapping("/topic/{id}")
    public Result<?> updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
        topic.setId(id);
        topicService.updateById(topic);
        return Result.success("更新成功");
    }
    
    @DeleteMapping("/topic/{id}")
    public Result<?> deleteTopic(@PathVariable Long id) {
        topicService.removeById(id);
        return Result.success("删除成功");
    }
    
    @GetMapping("/comment/list")
    public Result<IPage<Comment>> getCommentList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        Page<Comment> commentPage = new Page<>(page, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Comment::getCreateTime);
        
        IPage<Comment> result = commentService.page(commentPage, wrapper);
        return Result.success(result);
    }
    
    @DeleteMapping("/comment/{id}")
    public Result<?> deleteComment(@PathVariable Long id) {
        Comment comment = commentService.getById(id);
        comment.setStatus(2);
        commentService.updateById(comment);
        return Result.success("删除成功");
    }
    
    @GetMapping("/report/list")
    public Result<IPage<VideoReport>> getReportList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        Page<VideoReport> reportPage = new Page<>(page, size);
        LambdaQueryWrapper<VideoReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(VideoReport::getCreateTime);
        
        IPage<VideoReport> result = videoReportService.page(reportPage, wrapper);
        return Result.success(result);
    }
    
    @PutMapping("/report/{id}/handle")
    public Result<?> handleReport(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Integer status = (Integer) params.get("status");
        String handleResult = (String) params.get("handleResult");
        
        videoReportService.handleReport(id, status, handleResult);
        return Result.success("处理成功");
    }
}

