package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.Feedback;
import com.repair.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/list")
    public Result<Page<Feedback>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String feedbackType,
                                          @RequestParam(required = false) Long childId) {
        Page<Feedback> page = feedbackService.getList(pageNum, pageSize, feedbackType, childId);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Feedback feedback) {
        feedbackService.add(feedback);
        return Result.success();
    }
}

