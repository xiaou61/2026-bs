package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.ResumeFile;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.OperationLogService;
import com.recruitmatch.service.ResumeFileService;
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
@RequestMapping("/api/resume")
public class ResumeController {
    @Autowired
    private ResumeFileService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<ResumeFile>> page(@RequestAttribute Long userId,
                                      @RequestAttribute String role,
                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      String keyword,
                                      Long candidateId,
                                      Integer parseStatus) {
        authService.assertHrOrCandidate(role);
        return Result.success(service.pageByRole(pageNum, pageSize, keyword, candidateId, parseStatus, userId, role));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ResumeFile entity) {
        authService.assertHrOrCandidate(role);
        service.saveEntity(entity, userId, role);
        operationLogService.record(userId, "简历材料", "新增", "新增简历材料");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ResumeFile entity) {
        authService.assertHrOrCandidate(role);
        service.saveEntity(entity, userId, role);
        operationLogService.record(userId, "简历材料", "编辑", "编辑简历材料：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOrCandidate(role);
        service.deleteByRole(id, userId, role);
        operationLogService.record(userId, "简历材料", "删除", "删除简历材料：" + id);
        return Result.success();
    }
}
