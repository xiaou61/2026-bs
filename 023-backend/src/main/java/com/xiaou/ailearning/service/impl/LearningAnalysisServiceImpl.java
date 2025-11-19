package com.xiaou.ailearning.service.impl;

import com.xiaou.ailearning.entity.*;
import com.xiaou.ailearning.mapper.*;
import com.xiaou.ailearning.service.LearningAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LearningAnalysisServiceImpl implements LearningAnalysisService {

    @Autowired
    private LearningRecordMapper learningRecordMapper;
    
    @Autowired
    private UserBehaviorMapper userBehaviorMapper;
    
    @Autowired
    private KnowledgePointMapper knowledgePointMapper;
    
    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> generateLearningReport(Long userId) {
        Map<String, Object> report = new HashMap<>();
        
        Map<String, Object> basicStats = calculateLearningStatistics(userId);
        Map<String, Double> masteryAnalysis = analyzeKnowledgeMastery(userId);
        List<Long> weakPoints = identifyWeakKnowledgePoints(userId);
        Map<String, Object> learningPattern = analyzeLearningPattern(userId);
        Map<String, Object> efficiency = analyzeLearningEfficiency(userId);
        List<Map<String, Object>> insights = generateLearningInsights(userId);
        
        report.put("basicStatistics", basicStats);
        report.put("knowledgeMastery", masteryAnalysis);
        report.put("weakKnowledgePoints", weakPoints);
        report.put("learningPattern", learningPattern);
        report.put("learningEfficiency", efficiency);
        report.put("insights", insights);
        report.put("generatedAt", LocalDateTime.now());
        
        return report;
    }

    @Override
    public Map<String, Double> analyzeKnowledgeMastery(Long userId) {
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        Map<String, Double> masteryMap = new HashMap<>();
        
        if (records.isEmpty()) {
            return masteryMap;
        }
        
        Map<Long, List<LearningRecord>> groupedByKnowledge = records.stream()
                .filter(r -> r.getKnowledgePointId() != null && r.getMasteryLevel() != null)
                .collect(Collectors.groupingBy(LearningRecord::getKnowledgePointId));
        
        for (Map.Entry<Long, List<LearningRecord>> entry : groupedByKnowledge.entrySet()) {
            Long knowledgeId = entry.getKey();
            List<LearningRecord> knowledgeRecords = entry.getValue();
            
            double latestMastery = knowledgeRecords.stream()
                    .max(Comparator.comparing(LearningRecord::getCreateTime))
                    .map(LearningRecord::getMasteryLevel)
                    .orElse(0.0);
            
            double avgMastery = knowledgeRecords.stream()
                    .mapToDouble(LearningRecord::getMasteryLevel)
                    .average()
                    .orElse(0.0);
            
            double timeDecayFactor = calculateTimeDecayFactor(knowledgeRecords);
            double finalMastery = (latestMastery * 0.6 + avgMastery * 0.4) * timeDecayFactor;
            
            KnowledgePoint kp = knowledgePointMapper.selectById(knowledgeId);
            String knowledgeName = kp != null ? kp.getPointName() : "知识点-" + knowledgeId;
            
            masteryMap.put(knowledgeName, Math.max(0.0, Math.min(1.0, finalMastery)));
        }
        
        return masteryMap;
    }

    @Override
    public List<Long> identifyWeakKnowledgePoints(Long userId) {
        Map<String, Double> mastery = analyzeKnowledgeMastery(userId);
        
        return mastery.entrySet().stream()
                .filter(entry -> entry.getValue() < 0.6)
                .sorted(Map.Entry.comparingByValue())
                .limit(10)
                .map(entry -> {
                    String knowledgeName = entry.getKey();
                    return knowledgePointMapper.selectList(null).stream()
                            .filter(kp -> knowledgeName.equals(kp.getPointName()))
                            .map(KnowledgePoint::getId)
                            .findFirst()
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> analyzeLearningPattern(Long userId) {
        Map<String, Object> pattern = new HashMap<>();
        
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        List<UserBehavior> behaviors = userBehaviorMapper.selectByUserId(userId);
        
        if (records.isEmpty()) {
            return pattern;
        }
        
        Map<String, Integer> learningTypeDistribution = records.stream()
                .collect(Collectors.groupingBy(
                        r -> getLearningTypeName(r.getLearningType()),
                        Collectors.summingInt(r -> 1)
                ));
        pattern.put("learningTypeDistribution", learningTypeDistribution);
        
        Map<Integer, Long> hourlyActivity = records.stream()
                .filter(r -> r.getStartTime() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getStartTime().getHour(),
                        Collectors.counting()
                ));
        pattern.put("hourlyActivity", hourlyActivity);
        
        Map<String, Double> weeklyPattern = calculateWeeklyPattern(records);
        pattern.put("weeklyPattern", weeklyPattern);
        
        double avgSessionDuration = records.stream()
                .filter(r -> r.getDurationSeconds() != null)
                .mapToInt(LearningRecord::getDurationSeconds)
                .average()
                .orElse(0.0) / 60.0;
        pattern.put("avgSessionDurationMinutes", Math.round(avgSessionDuration * 100.0) / 100.0);
        
        Map<String, Object> focusAnalysis = analyzeFocusPatterns(records);
        pattern.put("focusAnalysis", focusAnalysis);
        
        return pattern;
    }

    @Override
    public double predictLearningProgress(Long userId, Long knowledgePointId) {
        List<LearningRecord> userRecords = learningRecordMapper.selectByUserId(userId);
        
        if (userRecords.isEmpty()) {
            return 0.5;
        }
        
        double userAbility = userRecords.stream()
                .filter(r -> r.getMasteryLevel() != null)
                .mapToDouble(LearningRecord::getMasteryLevel)
                .average()
                .orElse(0.5);
        
        KnowledgePoint kp = knowledgePointMapper.selectById(knowledgePointId);
        if (kp == null) {
            return userAbility;
        }
        
        double difficultyFactor = 1.0;
        if (kp.getDifficultyLevel() != null) {
            difficultyFactor = 1.0 - (kp.getDifficultyLevel() - 1) * 0.1;
        }
        
        List<LearningRecord> similarRecords = userRecords.stream()
                .filter(r -> r.getKnowledgePointId() != null)
                .filter(r -> Objects.equals(r.getKnowledgePointId(), knowledgePointId))
                .collect(Collectors.toList());
        
        double historicalPerformance = similarRecords.stream()
                .mapToDouble(LearningRecord::getMasteryLevel)
                .average()
                .orElse(userAbility);
        
        double prediction = (userAbility * 0.4 + historicalPerformance * 0.4 + difficultyFactor * 0.2);
        return Math.max(0.0, Math.min(1.0, prediction));
    }

    @Override
    public Map<String, Object> analyzeLearningEfficiency(Long userId) {
        Map<String, Object> efficiency = new HashMap<>();
        
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        
        if (records.isEmpty()) {
            return efficiency;
        }
        
        double avgMasteryPerHour = records.stream()
                .filter(r -> r.getDurationSeconds() != null && r.getDurationSeconds() > 0 && r.getMasteryLevel() != null)
                .mapToDouble(r -> r.getMasteryLevel() / (r.getDurationSeconds() / 3600.0))
                .average()
                .orElse(0.0);
        
        efficiency.put("masteryPerHour", Math.round(avgMasteryPerHour * 100.0) / 100.0);
        
        double completionRate = (double) records.stream()
                .filter(r -> r.getCompletionRate() != null && r.getCompletionRate() >= 80)
                .count() / records.size() * 100;
        
        efficiency.put("completionRate", Math.round(completionRate * 100.0) / 100.0);
        
        double avgFocusRate = records.stream()
                .filter(r -> r.getFocusDuration() != null && r.getDurationSeconds() != null && r.getDurationSeconds() > 0)
                .mapToDouble(r -> (double) r.getFocusDuration() / r.getDurationSeconds())
                .average()
                .orElse(0.0) * 100;
        
        efficiency.put("focusRate", Math.round(avgFocusRate * 100.0) / 100.0);
        
        Map<String, Double> efficiencyTrend = calculateEfficiencyTrend(records);
        efficiency.put("trend", efficiencyTrend);
        
        return efficiency;
    }

    @Override
    public List<Map<String, Object>> generateLearningInsights(Long userId) {
        List<Map<String, Object>> insights = new ArrayList<>();
        
        Map<String, Double> mastery = analyzeKnowledgeMastery(userId);
        Map<String, Object> pattern = analyzeLearningPattern(userId);
        Map<String, Object> efficiency = analyzeLearningEfficiency(userId);
        
        if (!mastery.isEmpty()) {
            double avgMastery = mastery.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            if (avgMastery > 0.8) {
                insights.add(createInsight("positive", "学习掌握度优秀", 
                    "您的平均知识点掌握度达到" + Math.round(avgMastery * 100) + "%，表现非常出色！"));
            } else if (avgMastery < 0.5) {
                insights.add(createInsight("warning", "需要加强学习", 
                    "您的平均知识点掌握度为" + Math.round(avgMastery * 100) + "%，建议加强基础知识的学习。"));
            }
        }
        
        if (pattern.containsKey("avgSessionDurationMinutes")) {
            double avgDuration = (Double) pattern.get("avgSessionDurationMinutes");
            if (avgDuration > 120) {
                insights.add(createInsight("warning", "学习时间过长", 
                    "您的平均学习时长为" + Math.round(avgDuration) + "分钟，建议适当休息，提高学习效率。"));
            } else if (avgDuration < 30) {
                insights.add(createInsight("info", "学习时间较短", 
                    "您的平均学习时长为" + Math.round(avgDuration) + "分钟，可以考虑延长学习时间以提高学习效果。"));
            }
        }
        
        if (efficiency.containsKey("focusRate")) {
            double focusRate = (Double) efficiency.get("focusRate");
            if (focusRate > 80) {
                insights.add(createInsight("positive", "专注度优秀", 
                    "您的学习专注度达到" + Math.round(focusRate) + "%，保持良好的学习习惯！"));
            } else if (focusRate < 50) {
                insights.add(createInsight("warning", "专注度需要提升", 
                    "您的学习专注度为" + Math.round(focusRate) + "%，建议减少干扰，提高专注度。"));
            }
        }
        
        return insights;
    }

    @Override
    public void recordLearningBehavior(UserBehavior behavior) {
        behavior.setCreateTime(LocalDateTime.now());
        userBehaviorMapper.insert(behavior);
    }

    @Override
    public Map<String, Object> analyzeUserEngagement(Long userId) {
        Map<String, Object> engagement = new HashMap<>();
        
        List<UserBehavior> behaviors = userBehaviorMapper.selectByUserId(userId);
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        
        if (behaviors.isEmpty() && records.isEmpty()) {
            return engagement;
        }
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minus(7, ChronoUnit.DAYS);
        
        long recentActivities = behaviors.stream()
                .filter(b -> b.getCreateTime() != null && b.getCreateTime().isAfter(weekAgo))
                .count();
        
        engagement.put("weeklyActivityCount", recentActivities);
        
        long loginCount = behaviors.stream()
                .filter(b -> "login".equals(b.getBehaviorType()))
                .filter(b -> b.getCreateTime() != null && b.getCreateTime().isAfter(weekAgo))
                .count();
        
        engagement.put("weeklyLoginCount", loginCount);
        
        double avgDailyLearningTime = records.stream()
                .filter(r -> r.getCreateTime() != null && r.getCreateTime().isAfter(weekAgo))
                .filter(r -> r.getDurationSeconds() != null)
                .mapToInt(LearningRecord::getDurationSeconds)
                .average()
                .orElse(0.0) / 60.0;
        
        engagement.put("avgDailyLearningMinutes", Math.round(avgDailyLearningTime * 100.0) / 100.0);
        
        String engagementLevel = calculateEngagementLevel(recentActivities, loginCount, avgDailyLearningTime);
        engagement.put("engagementLevel", engagementLevel);
        
        return engagement;
    }

    @Override
    public Map<String, Object> calculateLearningStatistics(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        
        stats.put("totalLearningRecords", records.size());
        
        int totalLearningTimeMinutes = records.stream()
                .filter(r -> r.getDurationSeconds() != null)
                .mapToInt(LearningRecord::getDurationSeconds)
                .sum() / 60;
        stats.put("totalLearningTimeMinutes", totalLearningTimeMinutes);
        
        long distinctCourses = records.stream()
                .filter(r -> r.getCourseId() != null)
                .map(LearningRecord::getCourseId)
                .distinct()
                .count();
        stats.put("coursesLearned", distinctCourses);
        
        long distinctKnowledgePoints = records.stream()
                .filter(r -> r.getKnowledgePointId() != null)
                .map(LearningRecord::getKnowledgePointId)
                .distinct()
                .count();
        stats.put("knowledgePointsLearned", distinctKnowledgePoints);
        
        double avgScore = records.stream()
                .filter(r -> r.getScore() != null)
                .mapToDouble(LearningRecord::getScore)
                .average()
                .orElse(0.0);
        stats.put("avgScore", Math.round(avgScore * 100.0) / 100.0);
        
        double avgMasteryLevel = records.stream()
                .filter(r -> r.getMasteryLevel() != null)
                .mapToDouble(LearningRecord::getMasteryLevel)
                .average()
                .orElse(0.0);
        stats.put("avgMasteryLevel", Math.round(avgMasteryLevel * 100.0) / 100.0);
        
        return stats;
    }
    
    private double calculateTimeDecayFactor(List<LearningRecord> records) {
        if (records.isEmpty()) {
            return 1.0;
        }
        
        LocalDateTime latestTime = records.stream()
                .map(LearningRecord::getCreateTime)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(LocalDateTime.now());
        
        long daysSinceLastLearning = ChronoUnit.DAYS.between(latestTime, LocalDateTime.now());
        
        return Math.max(0.3, Math.exp(-daysSinceLastLearning * 0.05));
    }
    
    private String getLearningTypeName(Integer type) {
        if (type == null) return "未知";
        switch (type) {
            case 1: return "观看";
            case 2: return "练习";
            case 3: return "测试";
            case 4: return "复习";
            default: return "其他";
        }
    }
    
    private Map<String, Double> calculateWeeklyPattern(List<LearningRecord> records) {
        Map<String, Double> weeklyPattern = new HashMap<>();
        String[] weekdays = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        
        Map<Integer, Long> dayOfWeekCount = records.stream()
                .filter(r -> r.getCreateTime() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getCreateTime().getDayOfWeek().getValue(),
                        Collectors.counting()
                ));
        
        for (int i = 1; i <= 7; i++) {
            String dayName = weekdays[i - 1];
            long count = dayOfWeekCount.getOrDefault(i, 0L);
            weeklyPattern.put(dayName, (double) count);
        }
        
        return weeklyPattern;
    }
    
    private Map<String, Object> analyzeFocusPatterns(List<LearningRecord> records) {
        Map<String, Object> focusAnalysis = new HashMap<>();
        
        List<LearningRecord> validRecords = records.stream()
                .filter(r -> r.getFocusDuration() != null && r.getDurationSeconds() != null && r.getDurationSeconds() > 0)
                .collect(Collectors.toList());
        
        if (validRecords.isEmpty()) {
            focusAnalysis.put("avgFocusRate", 0.0);
            focusAnalysis.put("focusDistribution", new HashMap<>());
            return focusAnalysis;
        }
        
        double avgFocusRate = validRecords.stream()
                .mapToDouble(r -> (double) r.getFocusDuration() / r.getDurationSeconds())
                .average()
                .orElse(0.0) * 100;
        
        focusAnalysis.put("avgFocusRate", Math.round(avgFocusRate * 100.0) / 100.0);
        
        Map<String, Long> focusDistribution = validRecords.stream()
                .collect(Collectors.groupingBy(r -> {
                    double focusRate = (double) r.getFocusDuration() / r.getDurationSeconds();
                    if (focusRate >= 0.8) return "高专注";
                    else if (focusRate >= 0.5) return "中等专注";
                    else return "低专注";
                }, Collectors.counting()));
        
        focusAnalysis.put("focusDistribution", focusDistribution);
        
        return focusAnalysis;
    }
    
    private Map<String, Double> calculateEfficiencyTrend(List<LearningRecord> records) {
        Map<String, Double> trend = new HashMap<>();
        
        LocalDateTime now = LocalDateTime.now();
        List<LearningRecord> recent = records.stream()
                .filter(r -> r.getCreateTime() != null && r.getCreateTime().isAfter(now.minus(30, ChronoUnit.DAYS)))
                .sorted(Comparator.comparing(LearningRecord::getCreateTime))
                .collect(Collectors.toList());
        
        if (recent.size() < 10) {
            trend.put("trend", 0.0);
            trend.put("direction", "insufficient_data");
            return trend;
        }
        
        int midPoint = recent.size() / 2;
        List<LearningRecord> firstHalf = recent.subList(0, midPoint);
        List<LearningRecord> secondHalf = recent.subList(midPoint, recent.size());
        
        double firstHalfEfficiency = firstHalf.stream()
                .filter(r -> r.getMasteryLevel() != null && r.getDurationSeconds() != null && r.getDurationSeconds() > 0)
                .mapToDouble(r -> r.getMasteryLevel() / (r.getDurationSeconds() / 3600.0))
                .average()
                .orElse(0.0);
        
        double secondHalfEfficiency = secondHalf.stream()
                .filter(r -> r.getMasteryLevel() != null && r.getDurationSeconds() != null && r.getDurationSeconds() > 0)
                .mapToDouble(r -> r.getMasteryLevel() / (r.getDurationSeconds() / 3600.0))
                .average()
                .orElse(0.0);
        
        double trendValue = secondHalfEfficiency - firstHalfEfficiency;
        trend.put("trend", Math.round(trendValue * 1000.0) / 1000.0);
        
        if (trendValue > 0.1) {
            trend.put("direction", "improving");
        } else if (trendValue < -0.1) {
            trend.put("direction", "declining");
        } else {
            trend.put("direction", "stable");
        }
        
        return trend;
    }
    
    private Map<String, Object> createInsight(String type, String title, String description) {
        Map<String, Object> insight = new HashMap<>();
        insight.put("type", type);
        insight.put("title", title);
        insight.put("description", description);
        insight.put("timestamp", LocalDateTime.now());
        return insight;
    }
    
    private String calculateEngagementLevel(long activities, long logins, double avgLearningTime) {
        double score = 0.0;
        
        if (activities > 20) score += 3;
        else if (activities > 10) score += 2;
        else if (activities > 5) score += 1;
        
        if (logins > 5) score += 2;
        else if (logins > 2) score += 1;
        
        if (avgLearningTime > 60) score += 3;
        else if (avgLearningTime > 30) score += 2;
        else if (avgLearningTime > 10) score += 1;
        
        if (score >= 6) return "高度活跃";
        else if (score >= 4) return "中等活跃";
        else if (score >= 2) return "低度活跃";
        else return "不活跃";
    }
}