package com.xiaou.health.controller;

import com.xiaou.health.common.Constants;
import com.xiaou.health.common.Result;
import com.xiaou.health.entity.HealthAlert;
import com.xiaou.health.entity.HealthAssessment;
import com.xiaou.health.service.HealthAssessmentService;
import com.xiaou.health.util.UserContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-assessment")
public class HealthAssessmentController {
    private final HealthAssessmentService healthAssessmentService;

    public HealthAssessmentController(HealthAssessmentService healthAssessmentService) {
        this.healthAssessmentService = healthAssessmentService;
    }

    @PostMapping("/generate")
    public Result<HealthAssessment> generateAssessment() {
        try {
            if (!UserContext.hasRole(Constants.ROLE_PATIENT)) {
                return Result.error(403, "仅患者可生成健康评估");
            }
            return Result.success(healthAssessmentService.generateAssessment(UserContext.getUserId()));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/latest")
    public Result<HealthAssessment> getLatestAssessment() {
        try {
            if (!UserContext.hasRole(Constants.ROLE_PATIENT)) {
                return Result.error(403, "仅患者可查看健康评估");
            }
            return Result.success(healthAssessmentService.getLatestAssessment(UserContext.getUserId()));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/history")
    public Result<List<HealthAssessment>> getAssessmentHistory() {
        try {
            if (!UserContext.hasRole(Constants.ROLE_PATIENT)) {
                return Result.error(403, "仅患者可查看健康评估");
            }
            return Result.success(healthAssessmentService.getAssessmentHistory(UserContext.getUserId()));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/alerts")
    public Result<List<HealthAlert>> getAlerts(@RequestParam(required = false) Integer isRead) {
        try {
            if (!UserContext.hasRole(Constants.ROLE_PATIENT)) {
                return Result.error(403, "仅患者可查看健康预警");
            }
            return Result.success(healthAssessmentService.getAlerts(UserContext.getUserId(), isRead));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/alerts/unread-count")
    public Result<Long> getUnreadAlertCount() {
        try {
            if (!UserContext.hasRole(Constants.ROLE_PATIENT)) {
                return Result.error(403, "仅患者可查看健康预警");
            }
            return Result.success(healthAssessmentService.countUnreadAlerts(UserContext.getUserId()));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/alerts/{id}/read")
    public Result<HealthAlert> markAlertAsRead(@PathVariable Long id) {
        try {
            if (!UserContext.hasRole(Constants.ROLE_PATIENT)) {
                return Result.error(403, "仅患者可处理健康预警");
            }
            return Result.success(healthAssessmentService.markAlertAsRead(UserContext.getUserId(), id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
