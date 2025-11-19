package com.xiaou.ailearning.controller;

import com.xiaou.ailearning.common.Result;
import com.xiaou.ailearning.entity.LearningPath;
import com.xiaou.ailearning.service.LearningPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/learning-path")
public class LearningPathController {

    @Autowired
    private LearningPathService learningPathService;

    @PostMapping("/create")
    public Result<LearningPath> createLearningPath(@RequestAttribute Long userId, @RequestBody Map<String, Long> request) {
        try {
            Long targetKnowledgePointId = request.get("targetKnowledgePointId");
            if (targetKnowledgePointId == null) {
                return Result.error("目标知识点ID不能为空");
            }
            
            LearningPath path = learningPathService.createOptimalPath(userId, targetKnowledgePointId);
            return Result.success(path);
        } catch (Exception e) {
            return Result.error("创建学习路径失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<LearningPath>> getUserPaths(@RequestAttribute Long userId) {
        try {
            List<LearningPath> paths = learningPathService.getUserPaths(userId);
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
}