package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.ConsultationRecord;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.ConsultationRecordService;
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
@RequestMapping("/api/consultation")
public class ConsultationRecordController {
    @Autowired
    private ConsultationRecordService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<ConsultationRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long caseId,
                                          Long clientId,
                                          Long lawyerId,
                                          String riskLevel) {
        return Result.success(service.page(pageNum, pageSize, keyword, caseId, clientId, lawyerId, riskLevel));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ConsultationRecord consultationRecord) {
        authService.assertStaff(role);
        service.saveEntity(consultationRecord);
        operationLogService.record(userId, "咨询记录", "新增", "新增咨询记录");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ConsultationRecord consultationRecord) {
        authService.assertStaff(role);
        service.saveEntity(consultationRecord);
        operationLogService.record(userId, "咨询记录", "编辑", "编辑咨询记录：" + consultationRecord.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "咨询记录", "删除", "删除咨询记录：" + id);
        return Result.success();
    }
}
