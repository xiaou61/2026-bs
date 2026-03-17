package com.kindergarten.controller;

import com.github.pagehelper.PageInfo;
import com.kindergarten.common.Result;
import com.kindergarten.entity.ParentFeedback;
import com.kindergarten.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/list")
    public Result<PageInfo<ParentFeedback>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) Long childId,
                                                 @RequestAttribute("userId") Long userId,
                                                 @RequestAttribute("role") String role) {
        return Result.success(feedbackService.list(childId, userId, role, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ParentFeedback entity, @RequestAttribute("userId") Long userId) {
        feedbackService.add(entity, userId);
        return Result.success();
    }

    @PutMapping("/reply")
    public Result<String> reply(@RequestBody ParentFeedback entity,
                                @RequestAttribute("role") String role,
                                @RequestAttribute("userId") Long userId) {
        feedbackService.reply(entity, role, userId);
        return Result.success();
    }
}
