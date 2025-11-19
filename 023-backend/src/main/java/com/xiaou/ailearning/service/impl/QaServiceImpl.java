package com.xiaou.ailearning.service.impl;

import com.xiaou.ailearning.entity.KnowledgePoint;
import com.xiaou.ailearning.entity.QaRecord;
import com.xiaou.ailearning.mapper.KnowledgePointMapper;
import com.xiaou.ailearning.mapper.QaRecordMapper;
import com.xiaou.ailearning.service.QaService;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QaServiceImpl implements QaService {

    @Autowired
    private QaRecordMapper qaRecordMapper;
    
    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

    @Override
    public String answerQuestion(Long userId, String question, String sessionId) {
        long startTime = System.currentTimeMillis();
        
        List<String> keywords = extractKeywords(question);
        List<KnowledgePoint> relatedKnowledgePoints = findRelatedKnowledgePoints(question);
        
        String answer = generateAnswer(question, relatedKnowledgePoints);
        double answerScore = calculateAnswerRelevance(question, answer);
        
        QaRecord qaRecord = new QaRecord();
        qaRecord.setUserId(userId);
        qaRecord.setQuestion(question);
        qaRecord.setQuestionKeywords(String.join(",", keywords));
        qaRecord.setAnswer(answer);
        qaRecord.setAnswerScore(answerScore);
        qaRecord.setKnowledgePointIds(
            relatedKnowledgePoints.stream()
                .map(kp -> kp.getId().toString())
                .collect(Collectors.joining(","))
        );
        qaRecord.setSessionId(sessionId);
        qaRecord.setResponseTimeMs((int)(System.currentTimeMillis() - startTime));
        qaRecord.setCreateTime(LocalDateTime.now());
        
        qaRecordMapper.insert(qaRecord);
        
        return answer;
    }

    @Override
    public List<QaRecord> getQaHistory(Long userId, int limit) {
        List<QaRecord> allRecords = qaRecordMapper.selectByUserId(userId);
        return allRecords.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<QaRecord> getSessionHistory(String sessionId) {
        return qaRecordMapper.selectBySessionId(sessionId);
    }

    @Override
    public List<String> extractKeywords(String text) {
        text = text.toLowerCase().replaceAll("[^\\p{L}\\p{N}\\s]", "");
        
        Set<String> stopWords = Set.of("是", "的", "了", "在", "有", "和", "就", "不", "都", "而", "及", "与", "或", 
                "什么", "怎么", "如何", "为什么", "哪里", "什么时候", "谁", "哪个", "多少");
        
        String[] words = text.split("\\s+");
        List<String> keywords = new ArrayList<>();
        
        for (String word : words) {
            if (word.length() > 1 && !stopWords.contains(word)) {
                keywords.add(word);
            }
        }
        
        return keywords.stream()
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<KnowledgePoint> findRelatedKnowledgePoints(String question) {
        List<String> questionKeywords = extractKeywords(question);
        List<KnowledgePoint> allKnowledgePoints = knowledgePointMapper.selectList(null);
        
        Map<KnowledgePoint, Double> similarities = new HashMap<>();
        
        for (KnowledgePoint kp : allKnowledgePoints) {
            double similarity = calculateKeywordSimilarity(questionKeywords, kp);
            if (similarity > 0.1) {
                similarities.put(kp, similarity);
            }
        }
        
        return similarities.entrySet().stream()
                .sorted(Map.Entry.<KnowledgePoint, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public double calculateAnswerRelevance(String question, String answer) {
        List<String> questionKeywords = extractKeywords(question);
        List<String> answerKeywords = extractKeywords(answer);
        
        if (questionKeywords.isEmpty() || answerKeywords.isEmpty()) {
            return 0.5;
        }
        
        Set<String> commonKeywords = new HashSet<>(questionKeywords);
        commonKeywords.retainAll(answerKeywords);
        
        double keywordOverlap = (double) commonKeywords.size() / questionKeywords.size();
        double lengthFactor = Math.min(1.0, answer.length() / 100.0);
        
        return (keywordOverlap * 0.7 + lengthFactor * 0.3);
    }

    @Override
    public void saveFeedback(Long qaRecordId, Boolean isHelpful) {
        QaRecord qaRecord = qaRecordMapper.selectById(qaRecordId);
        if (qaRecord != null) {
            qaRecord.setIsHelpful(isHelpful);
            qaRecordMapper.updateById(qaRecord);
        }
    }

    @Override
    public Map<String, Object> analyzeQuestion(String question) {
        Map<String, Object> analysis = new HashMap<>();
        
        List<String> keywords = extractKeywords(question);
        analysis.put("keywords", keywords);
        
        String questionType = classifyQuestion(question);
        analysis.put("type", questionType);
        
        double complexity = calculateComplexity(question);
        analysis.put("complexity", complexity);
        
        List<KnowledgePoint> relatedKnowledgePoints = findRelatedKnowledgePoints(question);
        analysis.put("relatedKnowledgePoints", relatedKnowledgePoints);
        
        return analysis;
    }

    @Override
    public String generateAnswer(String question, List<KnowledgePoint> relatedKnowledgePoints) {
        if (relatedKnowledgePoints.isEmpty()) {
            return generateGenericAnswer(question);
        }
        
        StringBuilder answer = new StringBuilder();
        
        String questionType = classifyQuestion(question);
        switch (questionType) {
            case "definition":
                answer.append("根据相关知识点，");
                break;
            case "how":
                answer.append("关于您的问题，具体步骤如下：");
                break;
            case "why":
                answer.append("这是因为：");
                break;
            default:
                answer.append("根据相关资料，");
                break;
        }
        
        KnowledgePoint mainKnowledgePoint = relatedKnowledgePoints.get(0);
        answer.append(mainKnowledgePoint.getDescription());
        
        if (relatedKnowledgePoints.size() > 1) {
            answer.append(" 此外，还需要了解：");
            for (int i = 1; i < Math.min(3, relatedKnowledgePoints.size()); i++) {
                KnowledgePoint kp = relatedKnowledgePoints.get(i);
                answer.append(kp.getPointName()).append("；");
            }
        }
        
        answer.append(" 建议您进一步学习相关课程以深入理解。");
        
        return answer.toString();
    }
    
    private double calculateKeywordSimilarity(List<String> questionKeywords, KnowledgePoint kp) {
        List<String> kpKeywords = new ArrayList<>();
        
        if (kp.getKeywords() != null && !kp.getKeywords().isEmpty()) {
            kpKeywords.addAll(Arrays.asList(kp.getKeywords().split(",")));
        }
        
        if (kp.getPointName() != null) {
            kpKeywords.addAll(extractKeywords(kp.getPointName()));
        }
        
        if (kp.getDescription() != null) {
            kpKeywords.addAll(extractKeywords(kp.getDescription()));
        }
        
        if (questionKeywords.isEmpty() || kpKeywords.isEmpty()) {
            return 0.0;
        }
        
        Set<String> commonKeywords = new HashSet<>(questionKeywords);
        commonKeywords.retainAll(kpKeywords);
        
        return (double) commonKeywords.size() / Math.max(questionKeywords.size(), kpKeywords.size());
    }
    
    private String classifyQuestion(String question) {
        String lowerQuestion = question.toLowerCase();
        
        if (lowerQuestion.contains("是什么") || lowerQuestion.contains("什么是") ||
            lowerQuestion.contains("定义") || lowerQuestion.contains("含义")) {
            return "definition";
        } else if (lowerQuestion.contains("怎么") || lowerQuestion.contains("如何") ||
                  lowerQuestion.contains("怎样") || lowerQuestion.contains("方法")) {
            return "how";
        } else if (lowerQuestion.contains("为什么") || lowerQuestion.contains("原因") ||
                  lowerQuestion.contains("为何")) {
            return "why";
        } else if (lowerQuestion.contains("哪里") || lowerQuestion.contains("在哪") ||
                  lowerQuestion.contains("位置")) {
            return "where";
        } else if (lowerQuestion.contains("什么时候") || lowerQuestion.contains("何时") ||
                  lowerQuestion.contains("时间")) {
            return "when";
        } else {
            return "general";
        }
    }
    
    private double calculateComplexity(String question) {
        double complexity = 0.0;
        
        complexity += question.length() * 0.01;
        
        String[] complexWords = {"算法", "原理", "机制", "架构", "设计", "优化", "性能", "分析"};
        for (String word : complexWords) {
            if (question.contains(word)) {
                complexity += 0.2;
            }
        }
        
        long questionMarkCount = question.chars().filter(ch -> ch == '？' || ch == '?').count();
        complexity += questionMarkCount * 0.1;
        
        return Math.min(1.0, complexity);
    }
    
    private String generateGenericAnswer(String question) {
        String questionType = classifyQuestion(question);
        
        switch (questionType) {
            case "definition":
                return "很抱歉，我暂时没有找到该概念的准确定义。建议您查阅相关教材或向老师咨询。";
            case "how":
                return "关于您的问题，建议您查看相关课程资料，或者尝试分解问题并逐步解决。";
            case "why":
                return "这是一个很好的问题。建议您从基础概念开始学习，理解其背后的原理。";
            default:
                return "感谢您的提问。建议您查看相关学习资料，或者将问题描述得更具体一些，这样我能提供更准确的帮助。";
        }
    }
}