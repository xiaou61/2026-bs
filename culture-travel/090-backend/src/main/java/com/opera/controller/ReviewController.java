package com.opera.controller;

import com.github.pagehelper.PageInfo;
import com.opera.common.Result;
import com.opera.entity.ReviewRecord;
import com.opera.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService scoreService;

    @GetMapping("/list")
    public Result<PageInfo<ReviewRecord>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false) Long scheduleId,
                                              @RequestParam(required = false) Long memberId,
                                              @RequestAttribute("userId") Long userId,
                                              @RequestAttribute("role") String role) {
        return Result.success(scoreService.list(scheduleId, memberId, userId, role, pageNum, pageSize));
    }

    @PostMapping("/save")
    public Result<String> save(@RequestBody ReviewRecord entity,
                               @RequestAttribute("userId") Long userId,
                               @RequestAttribute("role") String role) {
        scoreService.save(entity, userId, role);
        return Result.success();
    }
}


