package com.xiaou.ailearning.service;

import com.xiaou.ailearning.entity.KnowledgePoint;
import com.xiaou.ailearning.entity.LearningPath;

import java.util.List;
import java.util.Map;

public interface LearningPathService {
    
    LearningPath createOptimalPath(Long userId, Long targetKnowledgePointId);
    
    List<LearningPath> getUserPaths(Long userId);
    
    LearningPath updatePathProgress(Long pathId, int currentStep);
    
    void deactivatePath(Long pathId);
    
    List<KnowledgePoint> calculateShortestPath(Long startKnowledgeId, Long targetKnowledgeId, Map<Long, Double> userAbility);
    
    Map<Long, Double> evaluateUserAbility(Long userId);
    
    double calculatePathOptimizationScore(List<KnowledgePoint> path, Map<Long, Double> userAbility);
    
    List<KnowledgePoint> adjustPathByDifficulty(List<KnowledgePoint> originalPath, Map<Long, Double> userAbility);
    
    int estimatePathDuration(List<KnowledgePoint> path, Map<Long, Double> userAbility);
    
    void generatePathRecommendations(Long userId);
}