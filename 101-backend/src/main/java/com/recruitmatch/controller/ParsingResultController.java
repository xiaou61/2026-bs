package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.ParsingResult;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.OperationLogService;
import com.recruitmatch.service.ParsingResultService;
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
@RequestMapping("/api/parse-result")
public class ParsingResultController {
    @Autowired
    private ParsingResultService service;
    @Autowired
    private AuthService authService;
    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<ParsingResult>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            Long resumeId,
                                            Long candidateId,
                                            Integer reviewStatus) {
        return Result.success(service.page(pageNum, pageSize, resumeId, candidateId, reviewStatus));
    }

    @PutMapping("/review")
    public Result<Void> review(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ParsingResult entity) {
        authService.assertHr(role);
        service.review(entity);
        operationLogService.record(userId, "解析结果", "复核", "复核解析结果：" + entity.getId());
        return Result.success();
    }
}
