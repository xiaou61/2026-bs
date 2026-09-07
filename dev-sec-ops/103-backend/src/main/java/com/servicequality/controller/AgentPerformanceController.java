package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.AgentPerformance;
import com.servicequality.service.AuthService;
import com.servicequality.service.OperationLogService;
import com.servicequality.service.AgentPerformanceService;
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
@RequestMapping("/api/performance")
public class AgentPerformanceController {
    @Autowired
    private AgentPerformanceService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<AgentPerformance>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, Long agentId, String rankLevel) {
        return Result.success(service.page(pageNum, pageSize, keyword, agentId, rankLevel));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AgentPerformance entity) {
        authService.assertSupervisor(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "坐席绩效", "新增", "新增坐席绩效");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AgentPerformance entity) {
        authService.assertSupervisor(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "坐席绩效", "编辑", "编辑坐席绩效：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertSupervisor(role);
        service.removeById(id);
        operationLogService.record(userId, "坐席绩效", "删除", "删除坐席绩效：" + id);
        return Result.success();
    }
}
