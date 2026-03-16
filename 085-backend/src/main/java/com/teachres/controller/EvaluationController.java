package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/my/list")
    public Result<PageInfo<Map<String, Object>>> myList(@RequestAttribute("userId") Long userId,
                                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(required = false) Long taskId,
                                                         @RequestParam(required = false) Long courseId) {
        return Result.success(evaluationService.myList(userId, taskId, courseId, pageNum, pageSize));
    }

    @GetMapping("/detail/{recordId}")
    public Result<Map<String, Object>> detail(@PathVariable Long recordId) {
        return Result.success(evaluationService.detail(recordId));
    }

    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Map<String, Object> params,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        evaluationService.submit(params, userId, role);
        return Result.success();
    }

    @GetMapping("/task/records")
    public Result<PageInfo<Map<String, Object>>> taskRecords(@RequestParam Long taskId,
                                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(evaluationService.taskRecords(taskId, pageNum, pageSize));
    }

    @GetMapping("/task/summary")
    public Result<Map<String, Object>> taskSummary(@RequestParam Long taskId) {
        return Result.success(evaluationService.taskSummary(taskId));
    }
}
