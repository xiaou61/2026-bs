package com.xiaou.ailearning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.ailearning.entity.*;
import com.xiaou.ailearning.mapper.*;
import com.xiaou.ailearning.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private KnowledgePointMapper knowledgePointMapper;
    
    @Autowired
    private LearningRecordMapper learningRecordMapper;
    
    @Autowired
    private RecommendationMapper recommendationMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Course> recommendCourses(Long userId, int limit) {
        List<Course> collaborativeResults = collaborativeFilteringRecommend(userId, limit / 2);
        List<Course> contentResults = contentBasedRecommend(userId, limit / 2);
        
        Set<Long> recommendedIds = new HashSet<>();
        List<Course> finalResults = new ArrayList<>();
        
        for (Course course : collaborativeResults) {
            if (!recommendedIds.contains(course.getId()) && finalResults.size() < limit) {
                finalResults.add(course);
                recommendedIds.add(course.getId());
            }
        }
        
        for (Course course : contentResults) {
            if (!recommendedIds.contains(course.getId()) && finalResults.size() < limit) {
                finalResults.add(course);
                recommendedIds.add(course.getId());
            }
        }
        
        while (finalResults.size() < limit) {
            List<Course> hotCourses = getHotCourses(limit - finalResults.size());
            for (Course course : hotCourses) {
                if (!recommendedIds.contains(course.getId())) {
                    finalResults.add(course);
                    recommendedIds.add(course.getId());
                    if (finalResults.size() >= limit) break;
                }
            }
            break;
        }
        
        return finalResults;
    }

    @Override
    public List<KnowledgePoint> recommendKnowledgePoints(Long userId, int limit) {
        Map<Long, Double> userProfile = getUserProfile(userId);
        List<LearningRecord> userRecords = learningRecordMapper.selectByUserId(userId);
        
        Set<Long> learnedKnowledgeIds = userRecords.stream()
                .map(LearningRecord::getKnowledgePointId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        
        List<KnowledgePoint> allKnowledgePoints = knowledgePointMapper.selectList(null);
        
        Map<KnowledgePoint, Double> scores = new HashMap<>();
        for (KnowledgePoint kp : allKnowledgePoints) {
            if (!learnedKnowledgeIds.contains(kp.getId())) {
                double score = calculateKnowledgePointScore(kp, userProfile, userId);
                scores.put(kp, score);
            }
        }
        
        return scores.entrySet().stream()
                .sorted(Map.Entry.<KnowledgePoint, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> collaborativeFilteringRecommend(Long userId, int limit) {
        List<LearningRecord> userRecords = learningRecordMapper.selectByUserId(userId);
        if (userRecords.isEmpty()) {
            return getHotCourses(limit);
        }
        
        Map<Long, Double> userCourseRatings = userRecords.stream()
                .filter(record -> record.getCourseId() != null && record.getMasteryLevel() != null)
                .collect(Collectors.groupingBy(
                        LearningRecord::getCourseId,
                        Collectors.averagingDouble(LearningRecord::getMasteryLevel)
                ));
        
        List<User> allUsers = userMapper.selectList(null);
        Map<Long, Double> userSimilarities = new HashMap<>();
        
        for (User otherUser : allUsers) {
            if (!otherUser.getId().equals(userId)) {
                List<LearningRecord> otherRecords = learningRecordMapper.selectByUserId(otherUser.getId());
                Map<Long, Double> otherCourseRatings = otherRecords.stream()
                        .filter(record -> record.getCourseId() != null && record.getMasteryLevel() != null)
                        .collect(Collectors.groupingBy(
                                LearningRecord::getCourseId,
                                Collectors.averagingDouble(LearningRecord::getMasteryLevel)
                        ));
                
                double similarity = calculateCosineSimilarity(userCourseRatings, otherCourseRatings);
                if (similarity > 0.1) {
                    userSimilarities.put(otherUser.getId(), similarity);
                }
            }
        }
        
        List<Long> similarUsers = userSimilarities.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        Map<Long, Double> recommendedCourses = new HashMap<>();
        Set<Long> userLearnedCourses = userCourseRatings.keySet();
        
        for (Long similarUserId : similarUsers) {
            List<LearningRecord> similarUserRecords = learningRecordMapper.selectByUserId(similarUserId);
            for (LearningRecord record : similarUserRecords) {
                if (record.getCourseId() != null && 
                    !userLearnedCourses.contains(record.getCourseId()) &&
                    record.getMasteryLevel() != null && record.getMasteryLevel() > 0.6) {
                    
                    double weight = userSimilarities.get(similarUserId) * record.getMasteryLevel();
                    recommendedCourses.merge(record.getCourseId(), weight, Double::sum);
                }
            }
        }
        
        List<Long> topCourseIds = recommendedCourses.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        return topCourseIds.stream()
                .map(courseMapper::selectById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> contentBasedRecommend(Long userId, int limit) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return getHotCourses(limit);
        }
        
        List<LearningRecord> userRecords = learningRecordMapper.selectByUserId(userId);
        Set<Long> learnedCourseIds = userRecords.stream()
                .map(LearningRecord::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        
        List<Course> allCourses = courseMapper.selectList(null);
        Map<Course, Double> courseScores = new HashMap<>();
        
        for (Course course : allCourses) {
            if (!learnedCourseIds.contains(course.getId())) {
                double score = calculateContentBasedScore(user, course, userRecords);
                courseScores.put(course, score);
            }
        }
        
        return courseScores.entrySet().stream()
                .sorted(Map.Entry.<Course, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public void saveRecommendation(Recommendation recommendation) {
        recommendationMapper.insert(recommendation);
    }

    @Override
    public List<Recommendation> getUserRecommendations(Long userId) {
        return recommendationMapper.selectByUserId(userId);
    }

    @Override
    public double calculateCosineSimilarity(Map<Long, Double> profile1, Map<Long, Double> profile2) {
        Set<Long> commonItems = new HashSet<>(profile1.keySet());
        commonItems.retainAll(profile2.keySet());
        
        if (commonItems.isEmpty()) {
            return 0.0;
        }
        
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        for (Long item : commonItems) {
            double rating1 = profile1.get(item);
            double rating2 = profile2.get(item);
            
            dotProduct += rating1 * rating2;
            norm1 += rating1 * rating1;
            norm2 += rating2 * rating2;
        }
        
        if (norm1 == 0.0 || norm2 == 0.0) {
            return 0.0;
        }
        
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    @Override
    public Map<Long, Double> getUserProfile(Long userId) {
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        
        Map<Long, Double> courseRatings = records.stream()
                .filter(record -> record.getCourseId() != null && record.getMasteryLevel() != null)
                .collect(Collectors.groupingBy(
                        LearningRecord::getCourseId,
                        Collectors.averagingDouble(LearningRecord::getMasteryLevel)
                ));
        
        return courseRatings;
    }

    @Override
    public void updateUserProfile(Long userId, Long contentId, Integer contentType, Double rating) {
        Recommendation recommendation = new Recommendation();
        recommendation.setUserId(userId);
        recommendation.setContentId(contentId);
        recommendation.setContentType(contentType);
        recommendation.setRecommendationScore(rating);
        recommendation.setRecommendationType(1);
        recommendationMapper.insert(recommendation);
    }
    
    private List<Course> getHotCourses(int limit) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getStatus, 1)
               .orderByDesc(Course::getCreateTime)
               .last("LIMIT " + limit);
        return courseMapper.selectList(wrapper);
    }
    
    private double calculateContentBasedScore(User user, Course course, List<LearningRecord> userRecords) {
        double score = 0.0;
        
        if (user.getDifficultyPreference() != null && course.getDifficultyLevel() != null) {
            int difficultyMatch = Math.abs(user.getDifficultyPreference() - course.getDifficultyLevel());
            score += (5.0 - difficultyMatch) / 5.0 * 0.3;
        }
        
        if (user.getCognitiveLevel() != null && course.getDifficultyLevel() != null) {
            double expectedDifficulty = user.getCognitiveLevel() * 5;
            double difficultyAlignment = 1.0 - Math.abs(expectedDifficulty - course.getDifficultyLevel()) / 5.0;
            score += difficultyAlignment * 0.4;
        }
        
        double avgMastery = userRecords.stream()
                .filter(r -> r.getMasteryLevel() != null)
                .mapToDouble(LearningRecord::getMasteryLevel)
                .average()
                .orElse(0.5);
        
        score += avgMastery * 0.3;
        
        return Math.max(0.0, Math.min(1.0, score));
    }
    
    private double calculateKnowledgePointScore(KnowledgePoint kp, Map<Long, Double> userProfile, Long userId) {
        double score = 0.0;
        
        if (kp.getImportanceScore() != null) {
            score += kp.getImportanceScore() * 0.4;
        }
        
        User user = userMapper.selectById(userId);
        if (user != null && user.getDifficultyPreference() != null && kp.getDifficultyLevel() != null) {
            int difficultyMatch = Math.abs(user.getDifficultyPreference() - kp.getDifficultyLevel());
            score += (5.0 - difficultyMatch) / 5.0 * 0.3;
        }
        
        if (kp.getCourseId() != null && userProfile.containsKey(kp.getCourseId())) {
            score += userProfile.get(kp.getCourseId()) * 0.3;
        }
        
        return Math.max(0.0, Math.min(1.0, score));
    }
}