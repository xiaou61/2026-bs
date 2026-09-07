package com.xiaou.confession.controller;

import com.xiaou.confession.common.Result;
import com.xiaou.confession.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    
    private final LikeService likeService;
    
    @PostMapping
    public Result<Map<String, Object>> toggleLike(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Long targetId = Long.valueOf(params.get("targetId").toString());
            Integer targetType = (Integer) params.get("targetType");
            
            boolean liked = likeService.toggleLike(userId, targetId, targetType);
            
            return Result.success(Map.of("liked", liked));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/check")
    public Result<Map<String, Object>> checkLike(
            @RequestParam Long targetId,
            @RequestParam Integer targetType,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            boolean hasLiked = likeService.hasLiked(userId, targetId, targetType);
            
            return Result.success(Map.of("hasLiked", hasLiked));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

