package com.online.controller;

import com.online.common.Result;
import com.online.service.LearnRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/learn")
public class LearnController {
    @Autowired
    private LearnRecordService learnRecordService;

    @PostMapping("/start")
    public Result<?> startLearn(HttpServletRequest request, @RequestBody Map<String, Long> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long courseId = params.get("courseId");
        learnRecordService.startLearn(userId, courseId);
        return Result.success();
    }

    @GetMapping("/video/{id}")
    public Result<?> getVideoInfo(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(learnRecordService.getVideoInfo(userId, id));
    }

    @PostMapping("/progress")
    public Result<?> saveProgress(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long videoId = Long.valueOf(params.get("videoId").toString());
        Integer progress = (Integer) params.get("progress");
        learnRecordService.saveProgress(userId, videoId, progress);
        return Result.success();
    }

    @GetMapping("/my-courses")
    public Result<?> getMyCourses(HttpServletRequest request,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(learnRecordService.getMyCourses(userId, pageNum, pageSize));
    }

    @GetMapping("/records")
    public Result<?> getRecords(HttpServletRequest request,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(learnRecordService.getRecords(userId, pageNum, pageSize));
    }
}
