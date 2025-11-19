package com.xiaou.ailearning.controller;

import com.xiaou.ailearning.common.Result;
import com.xiaou.ailearning.entity.UserBehavior;
import com.xiaou.ailearning.service.LearningAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class LearningAnalysisController {

    @Autowired
    private LearningAnalysisService learningAnalysisService;

    @GetMapping("/report")
    public Result<Map<String, Object>> generateLearningReport(@RequestAttribute Long userId) {
        try {
            Map<String, Object> report = learningAnalysisService.generateLearningReport(userId);
            return Result.success(report);
        } catch (Exception e) {
            return Result.error("生成学习报告失败: " + e.getMessage());
        }
    }

    @GetMapping("/mastery")
    public Result<Map<String, Double>> getKnowledgeMastery(@RequestAttribute Long userId) {
        try {
            Map<String, Double> mastery = learningAnalysisService.analyzeKnowledgeMastery(userId);
            return Result.success(mastery);
        } catch (Exception e) {
            return Result.error("获取知识掌握度失败: " + e.getMessage());
        }
    }

    @GetMapping("/weak-points")
    public Result<List<Long>> getWeakKnowledgePoints(@RequestAttribute Long userId) {
        try {
            List<Long> weakPoints = learningAnalysisService.identifyWeakKnowledgePoints(userId);
            return Result.success(weakPoints);
        } catch (Exception e) {
            return Result.error("识别薄弱知识点失败: " + e.getMessage());
        }
    }

    @GetMapping("/pattern")
    public Result<Map<String, Object>> getLearningPattern(@RequestAttribute Long userId) {
        try {
            Map<String, Object> pattern = learningAnalysisService.analyzeLearningPattern(userId);
            return Result.success(pattern);
        } catch (Exception e) {
            return Result.error("分析学习模式失败: " + e.getMessage());
        }
    }

    @GetMapping("/efficiency")
    public Result<Map<String, Object>> getLearningEfficiency(@RequestAttribute Long userId) {
        try {
            Map<String, Object> efficiency = learningAnalysisService.analyzeLearningEfficiency(userId);
            return Result.success(efficiency);
        } catch (Exception e) {
            return Result.error("分析学习效率失败: " + e.getMessage());
        }
    }

    @GetMapping("/insights")
    public Result<List<Map<String, Object>>> getLearningInsights(@RequestAttribute Long userId) {
        try {
            List<Map<String, Object>> insights = learningAnalysisService.generateLearningInsights(userId);
            return Result.success(insights);
        } catch (Exception e) {
            return Result.error("生成学习洞察失败: " + e.getMessage());
        }
    }

    @GetMapping("/engagement")
    public Result<Map<String, Object>> getUserEngagement(@RequestAttribute Long userId) {
        try {
            Map<String, Object> engagement = learningAnalysisService.analyzeUserEngagement(userId);
            return Result.success(engagement);
        } catch (Exception e) {
            return Result.error("分析用户参与度失败: " + e.getMessage());
        }
    }

    @GetMapping("/statistics")
    public Result<Map<String, Object>> getLearningStatistics(@RequestAttribute Long userId) {
        try {
            Map<String, Object> statistics = learningAnalysisService.calculateLearningStatistics(userId);
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取学习统计失败: " + e.getMessage());
        }
    }

    @PostMapping("/predict-progress")
    public Result<Double> predictLearningProgress(@RequestAttribute Long userId, @RequestBody Map<String, Long> request) {
        try {
            Long knowledgePointId = request.get("knowledgePointId");
            if (knowledgePointId == null) {
                return Result.error("知识点ID不能为空");
            }
            
            double prediction = learningAnalysisService.predictLearningProgress(userId, knowledgePointId);
            return Result.success(prediction);
        } catch (Exception e) {
            return Result.error("预测学习进度失败: " + e.getMessage());
        }
    }

    @PostMapping("/behavior")
    public Result<Void> recordBehavior(@RequestAttribute Long userId, @RequestBody Map<String, Object> behaviorData, HttpServletRequest request) {
        try {
            UserBehavior behavior = new UserBehavior();
            behavior.setUserId(userId);
            behavior.setBehaviorType(behaviorData.get("behaviorType").toString());
            behavior.setTargetType((String) behaviorData.get("targetType"));
            behavior.setTargetId(behaviorData.get("targetId") != null ? Long.valueOf(behaviorData.get("targetId").toString()) : null);
            behavior.setBehaviorData(behaviorData.get("data") != null ? behaviorData.get("data").toString() : null);
            behavior.setSessionId((String) behaviorData.get("sessionId"));
            behavior.setIpAddress(getClientIpAddress(request));
            behavior.setUserAgent(request.getHeader("User-Agent"));
            
            learningAnalysisService.recordLearningBehavior(behavior);
            return Result.success("行为记录成功");
        } catch (Exception e) {
            return Result.error("记录行为失败: " + e.getMessage());
        }
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0];
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        return request.getRemoteAddr();
    }
}