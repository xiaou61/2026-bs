package com.xiaou.campusclub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.common.Result;
import com.xiaou.campusclub.dto.CommentRequest;
import com.xiaou.campusclub.dto.TopicRequest;
import com.xiaou.campusclub.service.TopicService;
import com.xiaou.campusclub.vo.CommentVO;
import com.xiaou.campusclub.vo.TopicVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    
    @Autowired
    private TopicService topicService;
    
    @GetMapping
    public Result<IPage<TopicVO>> getTopicList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long clubId,
            @RequestParam(required = false) Long circleId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(topicService.getTopicList(page, size, clubId, circleId, userId));
    }
    
    @GetMapping("/{id}")
    public Result<TopicVO> getTopicDetail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(topicService.getTopicDetail(id, userId));
    }
    
    @PostMapping
    public Result<Long> createTopic(HttpServletRequest request, @RequestBody TopicRequest topicRequest) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(topicService.createTopic(userId, topicRequest));
    }
    
    @PutMapping("/{id}")
    public Result<Void> updateTopic(@PathVariable Long id, HttpServletRequest request, 
                                   @RequestBody TopicRequest topicRequest) {
        Long userId = (Long) request.getAttribute("userId");
        topicService.updateTopic(id, userId, topicRequest);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteTopic(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        topicService.deleteTopic(id, userId);
        return Result.success();
    }
    
    @PostMapping("/{id}/like")
    public Result<Void> likeTopic(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        topicService.likeTopic(id, userId);
        return Result.success();
    }
    
    @DeleteMapping("/{id}/like")
    public Result<Void> unlikeTopic(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        topicService.unlikeTopic(id, userId);
        return Result.success();
    }
    
    @GetMapping("/{id}/comments")
    public Result<List<CommentVO>> getTopicComments(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(topicService.getTopicComments(id, userId));
    }
    
    @PostMapping("/{id}/comments")
    public Result<Long> addComment(@PathVariable Long id, HttpServletRequest request, 
                                  @RequestBody CommentRequest commentRequest) {
        Long userId = (Long) request.getAttribute("userId");
        commentRequest.setTopicId(id);
        return Result.success(topicService.addComment(userId, commentRequest));
    }
    
    @PostMapping("/comments/{id}/like")
    public Result<Void> likeComment(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        topicService.likeComment(id, userId);
        return Result.success();
    }
}

