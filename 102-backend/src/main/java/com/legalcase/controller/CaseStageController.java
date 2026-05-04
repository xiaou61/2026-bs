package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.CaseStage;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.CaseStageService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/stage")
public class CaseStageController {
    @Autowired
    private CaseStageService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<CaseStage>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long caseId,
                                          Integer status) {
        return Result.success(service.page(pageNum, pageSize, keyword, caseId, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CaseStage caseStage) {
        authService.assertStaff(role);
        service.saveEntity(caseStage);
        operationLogService.record(userId, "进度节点", "新增", "新增进度节点");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CaseStage caseStage) {
        authService.assertStaff(role);
        service.saveEntity(caseStage);
        operationLogService.record(userId, "进度节点", "编辑", "编辑进度节点：" + caseStage.getId());
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.finish(id);
        operationLogService.record(userId, "进度节点", "完成", "完成节点：" + id);
        return Result.success();
    }

    @PutMapping("/reopen/{id}")
    public Result<Void> reopen(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.reopen(id);
        operationLogService.record(userId, "进度节点", "重开", "重开节点：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "进度节点", "删除", "删除进度节点：" + id);
        return Result.success();
    }
}
