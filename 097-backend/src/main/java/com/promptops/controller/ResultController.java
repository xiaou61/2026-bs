package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.EvaluationResult;
import com.promptops.service.AuthService;
import com.promptops.service.EvaluationResultService;
import com.promptops.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/result")
public class ResultController {

    @Autowired
    private EvaluationResultService evaluationResultService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<EvaluationResult>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               Long taskId,
                                               Integer reviewStatus) {
        return Result.success(evaluationResultService.page(pageNum, pageSize, taskId, reviewStatus));
    }

    @PutMapping("/review")
    public Result<Void> review(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody EvaluationResult entity) {
        authService.assertReviewer(role);
        evaluationResultService.review(entity);
        operationLogService.record(userId, "评测结果", "复核", "复核评测结果：" + entity.getId());
        return Result.success();
    }
}
