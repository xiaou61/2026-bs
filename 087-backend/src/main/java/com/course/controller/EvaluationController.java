package com.course.controller;

import com.github.pagehelper.PageInfo;
import com.course.common.Result;
import com.course.entity.CourseEvaluation;
import com.course.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/list")
    public Result<PageInfo<CourseEvaluation>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false) Long scheduleId,
                                                   @RequestAttribute("userId") Long userId,
                                                   @RequestAttribute("role") String role) {
        return Result.success(evaluationService.list(scheduleId, userId, role, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CourseEvaluation entity, @RequestAttribute("userId") Long userId) {
        evaluationService.add(entity, userId);
        return Result.success();
    }
}
