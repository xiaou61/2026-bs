package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.FeeRecord;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.FeeRecordService;
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
@RequestMapping("/api/fee")
public class FeeRecordController {
    @Autowired
    private FeeRecordService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<FeeRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long caseId,
                                          Long clientId,
                                          Integer payStatus) {
        return Result.success(service.page(pageNum, pageSize, keyword, caseId, clientId, payStatus));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody FeeRecord feeRecord) {
        authService.assertStaff(role);
        service.saveEntity(feeRecord);
        operationLogService.record(userId, "费用记录", "新增", "新增费用记录");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody FeeRecord feeRecord) {
        authService.assertStaff(role);
        service.saveEntity(feeRecord);
        operationLogService.record(userId, "费用记录", "编辑", "编辑费用记录：" + feeRecord.getId());
        return Result.success();
    }

    @PutMapping("/pay/{id}")
    public Result<Void> pay(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.pay(id);
        operationLogService.record(userId, "费用记录", "支付", "标记已支付：" + id);
        return Result.success();
    }

    @PutMapping("/refund/{id}")
    public Result<Void> refund(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.refund(id);
        operationLogService.record(userId, "费用记录", "退款", "标记退款：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "费用记录", "删除", "删除费用记录：" + id);
        return Result.success();
    }
}
