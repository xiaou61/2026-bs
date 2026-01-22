package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.CheckinDTO;
import com.xiaou.entity.StudyPlan;
import com.xiaou.service.StudyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @PostMapping("/progress")
    public Result<?> updateProgress(@RequestParam Long courseId,
                                   @RequestParam Long chapterId,
                                   @RequestParam Integer position,
                                   @RequestParam Integer duration,
                                   HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        studyService.updateStudyProgress(userId, courseId, chapterId, position, duration);
        return Result.success();
    }

    @GetMapping("/records")
    public Result<?> getRecords(@RequestParam(defaultValue = "1") int current,
                               @RequestParam(defaultValue = "10") int size,
                               HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(studyService.getStudyRecords(userId, current, size));
    }

    @GetMapping("/plans")
    public Result<?> getPlans(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(studyService.getMyPlans(userId));
    }

    @PostMapping("/plan")
    public Result<?> createPlan(@RequestBody StudyPlan plan, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        plan.setUserId(userId);
        studyService.createPlan(plan);
        return Result.success();
    }

    @PutMapping("/plan/{id}/progress")
    public Result<?> updatePlanProgress(@PathVariable Long id, @RequestParam Integer progress) {
        studyService.updatePlanProgress(id, progress);
        return Result.success();
    }

    @PostMapping("/checkin")
    public Result<?> checkin(@RequestBody CheckinDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        studyService.checkin(userId, dto);
        return Result.success();
    }

    @GetMapping("/checkin/records")
    public Result<?> getCheckinRecords(@RequestParam(defaultValue = "30") Integer days, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(studyService.getCheckinRecords(userId, days));
    }

    @GetMapping("/checkin/stats")
    public Result<?> getCheckinStats(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(studyService.getCheckinStats(userId));
    }
}
