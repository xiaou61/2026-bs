package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.MatchResult;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.MatchResultService;
import com.recruitmatch.service.OperationLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/match-result")
public class MatchResultController {
    @Autowired
    private MatchResultService service;
    @Autowired
    private AuthService authService;
    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<MatchResult>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          Long candidateId,
                                          Long jobId,
                                          String recommendLevel,
                                          Integer reviewStatus) {
        return Result.success(service.page(pageNum, pageSize, candidateId, jobId, recommendLevel, reviewStatus));
    }

    @PutMapping("/review")
    public Result<Void> review(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody MatchResult entity) {
        authService.assertHr(role);
        service.review(entity);
        operationLogService.record(userId, "匹配结果", "复核", "复核匹配结果：" + entity.getId());
        return Result.success();
    }
}
