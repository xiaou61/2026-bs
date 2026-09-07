package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.CandidateProfile;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.OperationLogService;
import com.recruitmatch.service.CandidateProfileService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {
    @Autowired
    private CandidateProfileService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<CandidateProfile>> page(@RequestAttribute Long userId,
                                      @RequestAttribute String role,
                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      String keyword,
                                      Integer status,
                                      String targetJob) {
        authService.assertHrOrCandidate(role);
        return Result.success(service.pageByRole(pageNum, pageSize, keyword, status, targetJob, userId, role));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CandidateProfile entity) {
        authService.assertHrOrCandidate(role);
        service.saveEntity(entity, userId, role);
        operationLogService.record(userId, "候选人档案", "新增", "新增候选人档案");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CandidateProfile entity) {
        authService.assertHrOrCandidate(role);
        service.saveEntity(entity, userId, role);
        operationLogService.record(userId, "候选人档案", "编辑", "编辑候选人档案：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOrCandidate(role);
        service.deleteByRole(id, userId, role);
        operationLogService.record(userId, "候选人档案", "删除", "删除候选人档案：" + id);
        return Result.success();
    }
}
