package com.opera.controller;

import com.github.pagehelper.PageInfo;
import com.opera.common.Result;
import com.opera.entity.AppreciationComment;
import com.opera.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService evaluationService;

    @GetMapping("/list")
    public Result<PageInfo<AppreciationComment>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false) Long scheduleId,
                                                   @RequestAttribute("userId") Long userId,
                                                   @RequestAttribute("role") String role) {
        return Result.success(evaluationService.list(scheduleId, userId, role, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody AppreciationComment entity, @RequestAttribute("userId") Long userId) {
        evaluationService.add(entity, userId);
        return Result.success();
    }
}


