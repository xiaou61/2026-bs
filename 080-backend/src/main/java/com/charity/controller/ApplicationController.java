package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.Application;
import com.charity.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/list")
    public Result<Page<Application>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "10") int pageSize,
                                             @RequestParam(required = false) Integer applyStatus) {
        Page<Application> page = applicationService.getList(pageNum, pageSize, applyStatus);
        return Result.success(page);
    }

    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Application application) {
        applicationService.submit(application);
        return Result.success();
    }

    @PutMapping("/review")
    public Result<String> review(@RequestBody Application application,
                                 @RequestAttribute("userId") String userId) {
        applicationService.review(application.getId(), application.getApplyStatus(),
                                 application.getReviewComment(), Long.parseLong(userId));
        return Result.success();
    }
}
