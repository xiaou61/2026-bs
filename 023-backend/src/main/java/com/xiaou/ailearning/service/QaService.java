package com.xiaou.ailearning.service;

import com.xiaou.ailearning.entity.KnowledgePoint;
import com.xiaou.ailearning.entity.QaRecord;

import java.util.List;
import java.util.Map;

public interface QaService {
    
    String answerQuestion(Long userId, String question, String sessionId);
    
    List<QaRecord> getQaHistory(Long userId, int limit);
    
    List<QaRecord> getSessionHistory(String sessionId);
    
    List<String> extractKeywords(String text);
    
    List<KnowledgePoint> findRelatedKnowledgePoints(String question);
    
    double calculateAnswerRelevance(String question, String answer);
    
    void saveFeedback(Long qaRecordId, Boolean isHelpful);
    
    Map<String, Object> analyzeQuestion(String question);
    
    String generateAnswer(String question, List<KnowledgePoint> relatedKnowledgePoints);
}