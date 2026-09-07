package com.xiaou.ailearning.controller;

import com.xiaou.ailearning.common.Result;
import com.xiaou.ailearning.entity.Course;
import com.xiaou.ailearning.entity.KnowledgePoint;
import com.xiaou.ailearning.entity.Recommendation;
import com.xiaou.ailearning.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/courses")
    public Result<List<Course>> recommendCourses(@RequestAttribute Long userId, @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Course> recommendations = recommendationService.recommendCourses(userId, limit);
            return Result.success(recommendations);
        } catch (Exception e) {
            return Result.error("推荐失败: " + e.getMessage());
        }
    }

    @GetMapping("/knowledge-points")
    public Result<List<KnowledgePoint>> recommendKnowledgePoints(@RequestAttribute Long userId, @RequestParam(defaultValue = "10") int limit) {
        try {
            List<KnowledgePoint> recommendations = recommendationService.recommendKnowledgePoints(userId, limit);
            return Result.success(recommendations);
        } catch (Exception e) {
            return Result.error("推荐失败: " + e.getMessage());
        }
    }

    @GetMapping("/collaborative")
    public Result<List<Course>> collaborativeRecommendations(@RequestAttribute Long userId, @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Course> recommendations = recommendationService.collaborativeFilteringRecommend(userId, limit);
            return Result.success(recommendations);
        } catch (Exception e) {
            return Result.error("协同过滤推荐失败: " + e.getMessage());
        }
    }

    @GetMapping("/content-based")
    public Result<List<Course>> contentBasedRecommendations(@RequestAttribute Long userId, @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Course> recommendations = recommendationService.contentBasedRecommend(userId, limit);
            return Result.success(recommendations);
        } catch (Exception e) {
            return Result.error("内容推荐失败: " + e.getMessage());
        }
    }

    @GetMapping("/history")
    public Result<List<Recommendation>> getRecommendationHistory(@RequestAttribute Long userId) {
        try {
            List<Recommendation> history = recommendationService.getUserRecommendations(userId);
            return Result.success(history);
        } catch (Exception e) {
            return Result.error("获取推荐历史失败: " + e.getMessage());
        }
    }

    @PostMapping("/feedback")
    public Result<Void> submitFeedback(@RequestAttribute Long userId, @RequestBody Map<String, Object> feedback) {
        try {
            Long contentId = Long.valueOf(feedback.get("contentId").toString());
            Integer contentType = Integer.valueOf(feedback.get("contentType").toString());
            Double rating = Double.valueOf(feedback.get("rating").toString());
            
            recommendationService.updateUserProfile(userId, contentId, contentType, rating);
            return Result.success("反馈提交成功");
        } catch (Exception e) {
            return Result.error("反馈提交失败: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public Result<Map<Long, Double>> getUserProfile(@RequestAttribute Long userId) {
        try {
            Map<Long, Double> profile = recommendationService.getUserProfile(userId);
            return Result.success(profile);
        } catch (Exception e) {
            return Result.error("获取用户画像失败: " + e.getMessage());
        }
    }
}