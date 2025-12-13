package com.xiaou.health.controller;

import com.xiaou.health.common.Result;
import com.xiaou.health.dto.HealthDataCreateRequest;
import com.xiaou.health.entity.HealthData;
import com.xiaou.health.service.HealthDataService;
import com.xiaou.health.util.UserContext;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/health-data")
public class HealthDataController {
    private final HealthDataService healthDataService;

    public HealthDataController(HealthDataService healthDataService) {
        this.healthDataService = healthDataService;
    }

    @PostMapping
    public Result<HealthData> addHealthData(@Valid @RequestBody HealthDataCreateRequest request) {
        try {
            Long userId = UserContext.getUserId();
            HealthData healthData = healthDataService.addHealthData(userId, request);
            return Result.success(healthData);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<HealthData>> getHealthDataList() {
        try {
            Long userId = UserContext.getUserId();
            List<HealthData> list = healthDataService.getPatientHealthData(userId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list/{dataType}")
    public Result<List<HealthData>> getHealthDataByType(@PathVariable String dataType) {
        try {
            Long userId = UserContext.getUserId();
            List<HealthData> list = healthDataService.getPatientHealthDataByType(userId, dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/period")
    public Result<List<HealthData>> getHealthDataByPeriod(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        try {
            Long userId = UserContext.getUserId();
            List<HealthData> list = healthDataService.getPatientHealthDataByPeriod(userId, start, end);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteHealthData(@PathVariable Long id) {
        try {
            Long userId = UserContext.getUserId();
            healthDataService.deleteHealthData(id, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
