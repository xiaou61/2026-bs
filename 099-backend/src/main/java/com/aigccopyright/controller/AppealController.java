package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.AppealRecord;
import com.aigccopyright.service.AppealRecordService;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.OperationLogService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/appeal")
public class AppealController {

    @Autowired
    private AppealRecordService appealRecordService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<AppealRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           String targetType,
                                           Integer status,
                                           String keyword) {
        return Result.success(appealRecordService.page(pageNum, pageSize, targetType, status, keyword));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AppealRecord entity) {
        authService.assertCreator(role);
        appealRecordService.saveEntity(entity, userId);
        operationLogService.record(userId, "申诉处理", "新增", "新增申诉：" + entity.getTargetType());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AppealRecord entity) {
        authService.assertCreator(role);
        appealRecordService.saveEntity(entity, userId);
        operationLogService.record(userId, "申诉处理", "编辑", "编辑申诉：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/handle")
    public Result<Void> handle(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> body) {
        authService.assertAuditor(role);
        Long id = Long.valueOf(String.valueOf(body.get("id")));
        Integer status = body.get("status") == null ? 1 : Integer.valueOf(String.valueOf(body.get("status")));
        String comment = body.get("handleComment") == null ? "" : String.valueOf(body.get("handleComment"));
        appealRecordService.handle(id, status, comment);
        operationLogService.record(userId, "申诉处理", "处理", "处理申诉：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertCreator(role);
        appealRecordService.removeById(id);
        operationLogService.record(userId, "申诉处理", "删除", "删除申诉：" + id);
        return Result.success();
    }
}
