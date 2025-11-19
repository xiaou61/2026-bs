package com.xiaou.ailearning.service;

import com.xiaou.ailearning.entity.Course;
import com.xiaou.ailearning.entity.KnowledgePoint;
import com.xiaou.ailearning.entity.Recommendation;

import java.util.List;
import java.util.Map;

public interface RecommendationService {
    
    List<Course> recommendCourses(Long userId, int limit);
    
    List<KnowledgePoint> recommendKnowledgePoints(Long userId, int limit);
    
    List<Course> collaborativeFilteringRecommend(Long userId, int limit);
    
    List<Course> contentBasedRecommend(Long userId, int limit);
    
    void saveRecommendation(Recommendation recommendation);
    
    List<Recommendation> getUserRecommendations(Long userId);
    
    double calculateCosineSimilarity(Map<Long, Double> userProfile, Map<Long, Double> itemProfile);
    
    Map<Long, Double> getUserProfile(Long userId);
    
    void updateUserProfile(Long userId, Long contentId, Integer contentType, Double rating);
}