package com.xiaou.ailearning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.ailearning.entity.*;
import com.xiaou.ailearning.mapper.*;
import com.xiaou.ailearning.service.LearningPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LearningPathServiceImpl implements LearningPathService {

    @Autowired
    private LearningPathMapper learningPathMapper;
    
    @Autowired
    private KnowledgePointMapper knowledgePointMapper;
    
    @Autowired
    private KnowledgeRelationMapper knowledgeRelationMapper;
    
    @Autowired
    private LearningRecordMapper learningRecordMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public LearningPath createOptimalPath(Long userId, Long targetKnowledgePointId) {
        Map<Long, Double> userAbility = evaluateUserAbility(userId);
        
        Long startKnowledgeId = findUserCurrentLevel(userId, targetKnowledgePointId);
        List<KnowledgePoint> optimalPath = calculateShortestPath(startKnowledgeId, targetKnowledgePointId, userAbility);
        
        if (optimalPath.isEmpty()) {
            optimalPath = createBasicPath(targetKnowledgePointId);
        }
        
        optimalPath = adjustPathByDifficulty(optimalPath, userAbility);
        double optimizationScore = calculatePathOptimizationScore(optimalPath, userAbility);
        int estimatedDuration = estimatePathDuration(optimalPath, userAbility);
        
        LearningPath learningPath = new LearningPath();
        learningPath.setUserId(userId);
        learningPath.setPathName("学习路径 - " + getKnowledgePointName(targetKnowledgePointId));
        learningPath.setTargetKnowledgePointId(targetKnowledgePointId);
        learningPath.setEstimatedDuration(estimatedDuration);
        learningPath.setCurrentStep(0);
        learningPath.setProgressRate(0.0);
        learningPath.setAlgorithmType("dijkstra_optimized");
        learningPath.setOptimizationScore(optimizationScore);
        learningPath.setIsActive(true);
        learningPath.setCreateTime(LocalDateTime.now());
        learningPath.setUpdateTime(LocalDateTime.now());
        
        try {
            List<Map<String, Object>> pathSteps = optimalPath.stream().map(kp -> {
                Map<String, Object> step = new HashMap<>();
                step.put("knowledgePointId", kp.getId());
                step.put("pointName", kp.getPointName());
                step.put("difficultyLevel", kp.getDifficultyLevel());
                step.put("estimatedTime", kp.getLearningTimeEstimate());
                step.put("completed", false);
                return step;
            }).collect(Collectors.toList());
            
            learningPath.setPathSteps(objectMapper.writeValueAsString(pathSteps));
        } catch (JsonProcessingException e) {
            learningPath.setPathSteps("[]");
        }
        
        learningPathMapper.insert(learningPath);
        return learningPath;
    }

    @Override
    public List<LearningPath> getUserPaths(Long userId) {
        return learningPathMapper.selectByUserId(userId);
    }

    @Override
    public LearningPath updatePathProgress(Long pathId, int currentStep) {
        LearningPath path = learningPathMapper.selectById(pathId);
        if (path != null) {
            path.setCurrentStep(currentStep);
            
            try {
                List<Map<String, Object>> pathSteps = objectMapper.readValue(path.getPathSteps(), List.class);
                if (currentStep >= 0 && currentStep < pathSteps.size()) {
                    pathSteps.get(currentStep).put("completed", true);
                }
                path.setPathSteps(objectMapper.writeValueAsString(pathSteps));
                
                double progressRate = (double) (currentStep + 1) / pathSteps.size() * 100;
                path.setProgressRate(progressRate);
            } catch (JsonProcessingException e) {
                // 保持原有进度
            }
            
            path.setUpdateTime(LocalDateTime.now());
            learningPathMapper.updateById(path);
        }
        return path;
    }

    @Override
    public void deactivatePath(Long pathId) {
        LearningPath path = learningPathMapper.selectById(pathId);
        if (path != null) {
            path.setIsActive(false);
            path.setUpdateTime(LocalDateTime.now());
            learningPathMapper.updateById(path);
        }
    }

    @Override
    public List<KnowledgePoint> calculateShortestPath(Long startKnowledgeId, Long targetKnowledgeId, Map<Long, Double> userAbility) {
        if (Objects.equals(startKnowledgeId, targetKnowledgeId)) {
            KnowledgePoint target = knowledgePointMapper.selectById(targetKnowledgeId);
            return target != null ? Arrays.asList(target) : new ArrayList<>();
        }
        
        Map<Long, List<KnowledgeRelation>> graph = buildKnowledgeGraph();
        Map<Long, Double> distances = new HashMap<>();
        Map<Long, Long> previous = new HashMap<>();
        PriorityQueue<PathNode> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
        Set<Long> visited = new HashSet<>();
        
        distances.put(startKnowledgeId, 0.0);
        queue.offer(new PathNode(startKnowledgeId, 0.0));
        
        while (!queue.isEmpty()) {
            PathNode current = queue.poll();
            
            if (visited.contains(current.knowledgeId)) {
                continue;
            }
            visited.add(current.knowledgeId);
            
            if (current.knowledgeId.equals(targetKnowledgeId)) {
                break;
            }
            
            List<KnowledgeRelation> neighbors = graph.getOrDefault(current.knowledgeId, new ArrayList<>());
            for (KnowledgeRelation relation : neighbors) {
                Long neighborId = relation.getToKnowledgeId();
                if (visited.contains(neighborId)) {
                    continue;
                }
                
                double weight = calculateEdgeWeight(relation, userAbility);
                double newDistance = distances.get(current.knowledgeId) + weight;
                
                if (newDistance < distances.getOrDefault(neighborId, Double.MAX_VALUE)) {
                    distances.put(neighborId, newDistance);
                    previous.put(neighborId, current.knowledgeId);
                    queue.offer(new PathNode(neighborId, newDistance));
                }
            }
        }
        
        return reconstructPath(previous, startKnowledgeId, targetKnowledgeId);
    }

    @Override
    public Map<Long, Double> evaluateUserAbility(Long userId) {
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        Map<Long, Double> abilityMap = new HashMap<>();
        
        if (records.isEmpty()) {
            User user = userMapper.selectById(userId);
            if (user != null && user.getCognitiveLevel() != null) {
                return Map.of(-1L, user.getCognitiveLevel());
            }
            return Map.of(-1L, 0.5);
        }
        
        Map<Long, List<LearningRecord>> groupedRecords = records.stream()
                .filter(r -> r.getKnowledgePointId() != null && r.getMasteryLevel() != null)
                .collect(Collectors.groupingBy(LearningRecord::getKnowledgePointId));
        
        for (Map.Entry<Long, List<LearningRecord>> entry : groupedRecords.entrySet()) {
            double avgMastery = entry.getValue().stream()
                    .mapToDouble(LearningRecord::getMasteryLevel)
                    .average()
                    .orElse(0.5);
            abilityMap.put(entry.getKey(), avgMastery);
        }
        
        double overallAbility = abilityMap.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.5);
        abilityMap.put(-1L, overallAbility);
        
        return abilityMap;
    }

    @Override
    public double calculatePathOptimizationScore(List<KnowledgePoint> path, Map<Long, Double> userAbility) {
        if (path.isEmpty()) {
            return 0.0;
        }
        
        double difficultyScore = 0.0;
        double prerequisiteScore = 0.0;
        double efficiencyScore = 0.0;
        
        double userOverallAbility = userAbility.getOrDefault(-1L, 0.5);
        
        for (int i = 0; i < path.size(); i++) {
            KnowledgePoint kp = path.get(i);
            
            if (kp.getDifficultyLevel() != null) {
                double expectedDifficulty = userOverallAbility * 5;
                double difficultyAlignment = 1.0 - Math.abs(expectedDifficulty - kp.getDifficultyLevel()) / 5.0;
                difficultyScore += difficultyAlignment;
            }
            
            if (i > 0) {
                boolean hasPrerequisite = checkPrerequisiteRelation(path.get(i-1).getId(), kp.getId());
                if (hasPrerequisite) {
                    prerequisiteScore += 1.0;
                }
            }
        }
        
        efficiencyScore = 1.0 / Math.max(1, path.size());
        
        double totalScore = (difficultyScore / path.size()) * 0.4 + 
                           (prerequisiteScore / Math.max(1, path.size() - 1)) * 0.4 + 
                           efficiencyScore * 0.2;
        
        return Math.max(0.0, Math.min(1.0, totalScore));
    }

    @Override
    public List<KnowledgePoint> adjustPathByDifficulty(List<KnowledgePoint> originalPath, Map<Long, Double> userAbility) {
        if (originalPath.isEmpty()) {
            return originalPath;
        }
        
        double userLevel = userAbility.getOrDefault(-1L, 0.5);
        List<KnowledgePoint> adjustedPath = new ArrayList<>();
        
        for (KnowledgePoint kp : originalPath) {
            adjustedPath.add(kp);
            
            if (kp.getDifficultyLevel() != null && kp.getDifficultyLevel() > userLevel * 5 + 1) {
                List<KnowledgePoint> bridgePoints = findBridgeKnowledgePoints(kp.getId(), userLevel);
                adjustedPath.addAll(adjustedPath.size() - 1, bridgePoints);
            }
        }
        
        return adjustedPath;
    }

    @Override
    public int estimatePathDuration(List<KnowledgePoint> path, Map<Long, Double> userAbility) {
        if (path.isEmpty()) {
            return 0;
        }
        
        double userLevel = userAbility.getOrDefault(-1L, 0.5);
        int totalMinutes = 0;
        
        for (KnowledgePoint kp : path) {
            int baseTime = kp.getLearningTimeEstimate() != null ? kp.getLearningTimeEstimate() : 60;
            
            double difficultyMultiplier = 1.0;
            if (kp.getDifficultyLevel() != null) {
                difficultyMultiplier = Math.max(0.5, 2.0 - userLevel * kp.getDifficultyLevel() * 0.2);
            }
            
            totalMinutes += (int) (baseTime * difficultyMultiplier);
        }
        
        return (totalMinutes + 59) / 60;
    }

    @Override
    public void generatePathRecommendations(Long userId) {
        Map<Long, Double> userAbility = evaluateUserAbility(userId);
        List<KnowledgePoint> allKnowledgePoints = knowledgePointMapper.selectList(null);
        
        List<LearningRecord> userRecords = learningRecordMapper.selectByUserId(userId);
        Set<Long> learnedKnowledgeIds = userRecords.stream()
                .map(LearningRecord::getKnowledgePointId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        
        List<KnowledgePoint> candidates = allKnowledgePoints.stream()
                .filter(kp -> !learnedKnowledgeIds.contains(kp.getId()))
                .filter(kp -> kp.getImportanceScore() != null && kp.getImportanceScore() > 0.7)
                .limit(5)
                .collect(Collectors.toList());
        
        for (KnowledgePoint candidate : candidates) {
            try {
                createOptimalPath(userId, candidate.getId());
            } catch (Exception e) {
                // 跳过错误的路径创建
            }
        }
    }
    
    private static class PathNode {
        Long knowledgeId;
        Double distance;
        
        PathNode(Long knowledgeId, Double distance) {
            this.knowledgeId = knowledgeId;
            this.distance = distance;
        }
    }
    
    private Map<Long, List<KnowledgeRelation>> buildKnowledgeGraph() {
        List<KnowledgeRelation> allRelations = knowledgeRelationMapper.selectList(null);
        Map<Long, List<KnowledgeRelation>> graph = new HashMap<>();
        
        for (KnowledgeRelation relation : allRelations) {
            graph.computeIfAbsent(relation.getFromKnowledgeId(), k -> new ArrayList<>()).add(relation);
        }
        
        return graph;
    }
    
    private double calculateEdgeWeight(KnowledgeRelation relation, Map<Long, Double> userAbility) {
        double baseWeight = relation.getWeight() != null ? relation.getWeight() : 1.0;
        double confidence = relation.getConfidence() != null ? relation.getConfidence() : 1.0;
        
        double userAbilityFactor = 1.0;
        if (userAbility.containsKey(relation.getToKnowledgeId())) {
            double mastery = userAbility.get(relation.getToKnowledgeId());
            userAbilityFactor = 2.0 - mastery;
        }
        
        return baseWeight * (2.0 - confidence) * userAbilityFactor;
    }
    
    private List<KnowledgePoint> reconstructPath(Map<Long, Long> previous, Long startId, Long targetId) {
        List<KnowledgePoint> path = new ArrayList<>();
        Long currentId = targetId;
        
        while (currentId != null) {
            KnowledgePoint kp = knowledgePointMapper.selectById(currentId);
            if (kp != null) {
                path.add(0, kp);
            }
            currentId = previous.get(currentId);
            if (Objects.equals(currentId, startId)) {
                KnowledgePoint startKp = knowledgePointMapper.selectById(startId);
                if (startKp != null) {
                    path.add(0, startKp);
                }
                break;
            }
        }
        
        return path;
    }
    
    private Long findUserCurrentLevel(Long userId, Long targetKnowledgePointId) {
        List<LearningRecord> records = learningRecordMapper.selectByUserId(userId);
        
        if (records.isEmpty()) {
            return findBasicKnowledgePoint(targetKnowledgePointId);
        }
        
        return records.stream()
                .filter(r -> r.getKnowledgePointId() != null && r.getMasteryLevel() != null)
                .filter(r -> r.getMasteryLevel() > 0.7)
                .max(Comparator.comparing(LearningRecord::getCreateTime))
                .map(LearningRecord::getKnowledgePointId)
                .orElse(findBasicKnowledgePoint(targetKnowledgePointId));
    }
    
    private Long findBasicKnowledgePoint(Long targetKnowledgePointId) {
        KnowledgePoint target = knowledgePointMapper.selectById(targetKnowledgePointId);
        if (target == null || target.getCourseId() == null) {
            return targetKnowledgePointId;
        }
        
        List<KnowledgePoint> courseKnowledgePoints = knowledgePointMapper.selectByCourseId(target.getCourseId());
        return courseKnowledgePoints.stream()
                .filter(kp -> kp.getDifficultyLevel() != null)
                .min(Comparator.comparing(KnowledgePoint::getDifficultyLevel))
                .map(KnowledgePoint::getId)
                .orElse(targetKnowledgePointId);
    }
    
    private List<KnowledgePoint> createBasicPath(Long targetKnowledgePointId) {
        KnowledgePoint target = knowledgePointMapper.selectById(targetKnowledgePointId);
        if (target == null) {
            return new ArrayList<>();
        }
        
        List<KnowledgePoint> path = new ArrayList<>();
        
        if (target.getCourseId() != null) {
            List<KnowledgePoint> courseKnowledgePoints = knowledgePointMapper.selectByCourseId(target.getCourseId());
            courseKnowledgePoints.sort(Comparator.comparing(kp -> kp.getDifficultyLevel() != null ? kp.getDifficultyLevel() : 1));
            path.addAll(courseKnowledgePoints);
        } else {
            path.add(target);
        }
        
        return path;
    }
    
    private String getKnowledgePointName(Long knowledgePointId) {
        KnowledgePoint kp = knowledgePointMapper.selectById(knowledgePointId);
        return kp != null ? kp.getPointName() : "未知知识点";
    }
    
    private boolean checkPrerequisiteRelation(Long fromId, Long toId) {
        LambdaQueryWrapper<KnowledgeRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowledgeRelation::getFromKnowledgeId, fromId)
               .eq(KnowledgeRelation::getToKnowledgeId, toId)
               .eq(KnowledgeRelation::getRelationType, 1);
        
        return knowledgeRelationMapper.selectCount(wrapper) > 0;
    }
    
    private List<KnowledgePoint> findBridgeKnowledgePoints(Long knowledgePointId, double userLevel) {
        return new ArrayList<>();
    }
}