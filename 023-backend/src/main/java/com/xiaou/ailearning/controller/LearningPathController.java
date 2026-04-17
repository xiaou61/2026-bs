package com.xiaou.ailearning.controller;

import com.xiaou.ailearning.common.Result;
import com.xiaou.ailearning.entity.KnowledgePoint;
import com.xiaou.ailearning.entity.LearningPath;
import com.xiaou.ailearning.mapper.KnowledgePointMapper;
import com.xiaou.ailearning.mapper.LearningPathMapper;
import com.xiaou.ailearning.service.LearningPathService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/learning-path")
public class LearningPathController {

    @Autowired
    private LearningPathService learningPathService;

    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

    @Autowired
    private LearningPathMapper learningPathMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/create")
    public Result<Map<String, Object>> createLearningPath(@RequestAttribute Long userId, @RequestBody Map<String, Object> request) {
        try {
            Long targetKnowledgePointId = resolveTargetKnowledgePointId(request);
            if (targetKnowledgePointId == null) {
                return Result.error("目标知识点ID不能为空");
            }

            LearningPath path = learningPathService.createOptimalPath(userId, targetKnowledgePointId);
            if (request.get("pathName") != null && !request.get("pathName").toString().isBlank()) {
                path.setPathName(request.get("pathName").toString().trim());
            }
            if (request.get("estimatedDuration") instanceof Number) {
                path.setEstimatedDuration(((Number) request.get("estimatedDuration")).intValue());
            }
            path.setUpdateTime(LocalDateTime.now());
            learningPathMapper.updateById(path);
            return Result.success(convertPathView(path));
        } catch (Exception e) {
            return Result.error("创建学习路径失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<Map<String, Object>>> getUserPaths(
            @RequestAttribute Long userId,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String subject) {
        try {
            List<Map<String, Object>> paths = learningPathService.getUserPaths(userId).stream()
                    .map(this::convertPathView)
                    .sorted(Comparator.comparing(
                            item -> Objects.toString(item.get("pathName"), ""),
                            Comparator.naturalOrder()
                    ))
                    .collect(Collectors.toList());

            if ("completed".equalsIgnoreCase(filter)) {
                paths = paths.stream()
                        .filter(item -> asDouble(item.get("progressRate")) >= 100.0)
                        .collect(Collectors.toList());
            } else if ("recommended".equalsIgnoreCase(filter)) {
                paths = paths.stream()
                        .sorted(Comparator.comparing(
                                item -> asDouble(item.get("optimizationScore")),
                                Comparator.reverseOrder()
                        ))
                        .collect(Collectors.toList());
            } else if ("popular".equalsIgnoreCase(filter)) {
                paths = paths.stream()
                        .sorted(Comparator.comparing(
                                item -> asInt(item.get("difficulty")),
                                Comparator.naturalOrder()
                        ))
                        .collect(Collectors.toList());
            }

            if (difficulty != null && !difficulty.isBlank()) {
                int difficultyLevel = Integer.parseInt(difficulty);
                paths = paths.stream()
                        .filter(item -> asInt(item.get("difficulty")) == difficultyLevel)
                        .collect(Collectors.toList());
            }

            if (subject != null && !subject.isBlank()) {
                String keyword = subject.trim();
                paths = paths.stream()
                        .filter(item -> Objects.toString(item.get("targetKnowledgePoint"), "").contains(keyword))
                        .collect(Collectors.toList());
            }
            return Result.success(paths);
        } catch (Exception e) {
            return Result.error("获取学习路径失败: " + e.getMessage());
        }
    }

    @PutMapping("/{pathId}/progress")
    public Result<LearningPath> updateProgress(@PathVariable Long pathId, @RequestBody Map<String, Integer> request) {
        try {
            Integer currentStep = request.get("currentStep");
            if (currentStep == null) {
                return Result.error("当前步骤不能为空");
            }
            
            LearningPath updatedPath = learningPathService.updatePathProgress(pathId, currentStep);
            return Result.success(updatedPath);
        } catch (Exception e) {
            return Result.error("更新进度失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{pathId}")
    public Result<Void> deactivatePath(@PathVariable Long pathId) {
        try {
            learningPathService.deactivatePath(pathId);
            return Result.success("学习路径已停用");
        } catch (Exception e) {
            return Result.error("停用学习路径失败: " + e.getMessage());
        }
    }

    @GetMapping("/ability-evaluation")
    public Result<Map<Long, Double>> evaluateAbility(@RequestAttribute Long userId) {
        try {
            Map<Long, Double> ability = learningPathService.evaluateUserAbility(userId);
            return Result.success(ability);
        } catch (Exception e) {
            return Result.error("能力评估失败: " + e.getMessage());
        }
    }

    @PostMapping("/generate-recommendations")
    public Result<Void> generateRecommendations(@RequestAttribute Long userId) {
        try {
            learningPathService.generatePathRecommendations(userId);
            return Result.success("路径推荐生成成功");
        } catch (Exception e) {
            return Result.error("生成路径推荐失败: " + e.getMessage());
        }
    }

    @PostMapping("/calculate-duration")
    public Result<Integer> calculateDuration(@RequestBody Map<String, Object> request) {
        try {
            // 这里需要解析路径和用户能力，简化实现
            return Result.success(60); // 返回预估的小时数
        } catch (Exception e) {
            return Result.error("计算时长失败: " + e.getMessage());
        }
    }

    private Long resolveTargetKnowledgePointId(Map<String, Object> request) {
        Object directId = request.get("targetKnowledgePointId");
        if (directId instanceof Number) {
            return ((Number) directId).longValue();
        }
        if (directId instanceof String && !((String) directId).isBlank()) {
            return Long.parseLong((String) directId);
        }

        Object targetKeyword = request.get("targetKnowledgePoint");
        if (targetKeyword != null && !targetKeyword.toString().isBlank()) {
            String keyword = targetKeyword.toString().trim();
            List<KnowledgePoint> matched = knowledgePointMapper.selectByKeyword(keyword);
            if (matched.isEmpty()) {
                matched = knowledgePointMapper.selectList(null).stream()
                        .filter(point -> point.getPointName() != null && point.getPointName().contains(keyword))
                        .collect(Collectors.toList());
            }
            if (!matched.isEmpty()) {
                return matched.get(0).getId();
            }
        }

        return knowledgePointMapper.selectList(null).stream()
                .sorted(Comparator.comparing(KnowledgePoint::getImportanceScore, Comparator.nullsLast(Double::compareTo)).reversed())
                .map(KnowledgePoint::getId)
                .findFirst()
                .orElse(null);
    }

    private Map<String, Object> convertPathView(LearningPath path) {
        Map<String, Object> item = new LinkedHashMap<>();
        List<Map<String, Object>> pathSteps = parsePathSteps(path.getPathSteps()).stream()
                .map(step -> {
                    Map<String, Object> converted = new LinkedHashMap<>();
                    Long knowledgePointId = asLong(step.get("knowledgePointId"));
                    KnowledgePoint point = knowledgePointId == null ? null : knowledgePointMapper.selectById(knowledgePointId);
                    converted.put("id", knowledgePointId);
                    converted.put("stepName", Objects.toString(step.getOrDefault("pointName", point != null ? point.getPointName() : "学习步骤"), "学习步骤"));
                    converted.put("description", point != null ? point.getDescription() : "按照推荐顺序完成本步骤。");
                    converted.put("estimatedTime", asInt(step.getOrDefault("estimatedTime", point != null ? point.getLearningTimeEstimate() : 60)));
                    converted.put("difficulty", asInt(step.getOrDefault("difficultyLevel", point != null ? point.getDifficultyLevel() : 2)));
                    converted.put("stepType", "知识学习");
                    converted.put("progress", Boolean.TRUE.equals(step.get("completed")) ? 100 : 0);
                    converted.put("completed", Boolean.TRUE.equals(step.get("completed")));
                    converted.put("prerequisites", List.of());
                    return converted;
                })
                .collect(Collectors.toList());

        item.put("id", path.getId());
        item.put("pathName", path.getPathName());
        item.put("targetKnowledgePoint", resolveKnowledgePointName(path.getTargetKnowledgePointId()));
        item.put("estimatedDuration", normalizeDuration(path.getEstimatedDuration()));
        item.put("progressRate", path.getProgressRate() == null ? 0.0 : path.getProgressRate());
        item.put("optimizationScore", path.getOptimizationScore() == null ? 0.0 : path.getOptimizationScore());
        item.put("difficulty", pathSteps.stream().map(step -> asInt(step.get("difficulty"))).max(Integer::compareTo).orElse(2));
        item.put("currentStep", path.getCurrentStep() == null ? 0 : path.getCurrentStep());
        item.put("pathSteps", pathSteps);
        return item;
    }

    private List<Map<String, Object>> parsePathSteps(String rawPathSteps) {
        if (rawPathSteps == null || rawPathSteps.isBlank()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(rawPathSteps, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception ignored) {
            return List.of();
        }
    }

    private String resolveKnowledgePointName(Long knowledgePointId) {
        KnowledgePoint point = knowledgePointId == null ? null : knowledgePointMapper.selectById(knowledgePointId);
        return point != null ? point.getPointName() : "目标知识点";
    }

    private int normalizeDuration(Integer duration) {
        if (duration == null) {
            return 240;
        }
        return duration < 24 ? duration * 60 : duration;
    }

    private Long asLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String && !((String) value).isBlank()) {
            return Long.parseLong((String) value);
        }
        return null;
    }

    private int asInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String && !((String) value).isBlank()) {
            return Integer.parseInt((String) value);
        }
        return 2;
    }

    private double asDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String && !((String) value).isBlank()) {
            return Double.parseDouble((String) value);
        }
        return 0.0;
    }
}
