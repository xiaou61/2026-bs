package com.xiaou.health.controller;

import com.xiaou.health.common.Constants;
import com.xiaou.health.common.Result;
import com.xiaou.health.entity.HealthKnowledge;
import com.xiaou.health.service.HealthKnowledgeService;
import com.xiaou.health.util.UserContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-knowledge")
public class HealthKnowledgeController {
    private final HealthKnowledgeService healthKnowledgeService;

    public HealthKnowledgeController(HealthKnowledgeService healthKnowledgeService) {
        this.healthKnowledgeService = healthKnowledgeService;
    }

    @GetMapping("/list")
    public Result<List<HealthKnowledge>> getPublishedKnowledge(@RequestParam(required = false) String category) {
        try {
            return Result.success(healthKnowledgeService.getPublishedKnowledge(category));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/admin/list")
    public Result<List<HealthKnowledge>> getAllKnowledge() {
        try {
            if (!UserContext.hasRole(Constants.ROLE_ADMIN)) {
                return Result.error(403, "仅管理员可查看知识库管理列表");
            }
            return Result.success(healthKnowledgeService.getAllKnowledge());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    public Result<HealthKnowledge> getKnowledgeDetail(@PathVariable Long id) {
        try {
            boolean allowPreviewUnpublished = UserContext.hasRole(Constants.ROLE_ADMIN);
            return Result.success(healthKnowledgeService.getKnowledgeDetail(id, allowPreviewUnpublished));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result<HealthKnowledge> createKnowledge(@RequestBody HealthKnowledge request) {
        try {
            if (!UserContext.hasRole(Constants.ROLE_ADMIN)) {
                return Result.error(403, "仅管理员可创建健康知识");
            }
            return Result.success(healthKnowledgeService.createKnowledge(UserContext.getUserId(), request));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<HealthKnowledge> updateKnowledge(@PathVariable Long id, @RequestBody HealthKnowledge request) {
        try {
            if (!UserContext.hasRole(Constants.ROLE_ADMIN)) {
                return Result.error(403, "仅管理员可编辑健康知识");
            }
            return Result.success(healthKnowledgeService.updateKnowledge(id, request));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public Result<HealthKnowledge> updateKnowledgeStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            if (!UserContext.hasRole(Constants.ROLE_ADMIN)) {
                return Result.error(403, "仅管理员可修改知识状态");
            }
            return Result.success(healthKnowledgeService.updateStatus(id, status));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteKnowledge(@PathVariable Long id) {
        try {
            if (!UserContext.hasRole(Constants.ROLE_ADMIN)) {
                return Result.error(403, "仅管理员可删除健康知识");
            }
            healthKnowledgeService.deleteKnowledge(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
