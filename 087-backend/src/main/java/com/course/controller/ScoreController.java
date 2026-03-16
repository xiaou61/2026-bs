package com.course.controller;

import com.github.pagehelper.PageInfo;
import com.course.common.Result;
import com.course.entity.ScoreRecord;
import com.course.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/list")
    public Result<PageInfo<ScoreRecord>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false) Long scheduleId,
                                              @RequestParam(required = false) Long studentId,
                                              @RequestAttribute("userId") Long userId,
                                              @RequestAttribute("role") String role) {
        return Result.success(scoreService.list(scheduleId, studentId, userId, role, pageNum, pageSize));
    }

    @PostMapping("/save")
    public Result<String> save(@RequestBody ScoreRecord entity,
                               @RequestAttribute("userId") Long userId,
                               @RequestAttribute("role") String role) {
        scoreService.save(entity, userId, role);
        return Result.success();
    }
}
