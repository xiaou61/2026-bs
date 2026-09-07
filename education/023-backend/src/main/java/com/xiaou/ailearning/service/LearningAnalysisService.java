package com.xiaou.ailearning.service;

import com.xiaou.ailearning.entity.LearningRecord;
import com.xiaou.ailearning.entity.UserBehavior;

import java.util.List;
import java.util.Map;

public interface LearningAnalysisService {
    
    Map<String, Object> generateLearningReport(Long userId);
    
    Map<String, Double> analyzeKnowledgeMastery(Long userId);
    
    List<Long> identifyWeakKnowledgePoints(Long userId);
    
    Map<String, Object> analyzeLearningPattern(Long userId);
    
    double predictLearningProgress(Long userId, Long knowledgePointId);
    
    Map<String, Object> analyzeLearningEfficiency(Long userId);
    
    List<Map<String, Object>> generateLearningInsights(Long userId);
    
    void recordLearningBehavior(UserBehavior behavior);
    
    Map<String, Object> analyzeUserEngagement(Long userId);
    
    Map<String, Object> calculateLearningStatistics(Long userId);
}