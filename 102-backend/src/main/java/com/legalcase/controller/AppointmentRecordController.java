package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.AppointmentRecord;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.AppointmentRecordService;
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
@RequestMapping("/api/appointment")
public class AppointmentRecordController {
    @Autowired
    private AppointmentRecordService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<AppointmentRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long caseId,
                                          Long clientId,
                                          Long lawyerId,
                                          Integer status) {
        return Result.success(service.page(pageNum, pageSize, keyword, caseId, clientId, lawyerId, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AppointmentRecord appointmentRecord) {
        authService.assertStaff(role);
        service.saveEntity(appointmentRecord);
        operationLogService.record(userId, "咨询预约", "新增", "新增咨询预约");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AppointmentRecord appointmentRecord) {
        authService.assertStaff(role);
        service.saveEntity(appointmentRecord);
        operationLogService.record(userId, "咨询预约", "编辑", "编辑咨询预约：" + appointmentRecord.getId());
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.updateStatus(id, 1);
        operationLogService.record(userId, "咨询预约", "确认", "确认预约：" + id);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<Void> cancel(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.updateStatus(id, 2);
        operationLogService.record(userId, "咨询预约", "取消", "取消预约：" + id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.updateStatus(id, 3);
        operationLogService.record(userId, "咨询预约", "完成", "完成预约：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "咨询预约", "删除", "删除咨询预约：" + id);
        return Result.success();
    }
}
