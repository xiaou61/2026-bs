package com.smartwarehouse.controller;

import com.github.pagehelper.PageInfo;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.AgvTask;
import com.smartwarehouse.service.AgvTaskService;
import com.smartwarehouse.service.AuthService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class AgvTaskController {
    private final AuthService authService;
    private final AgvTaskService service;

    @GetMapping("/page")
    public Result<PageInfo<AgvTask>> page(@RequestAttribute String role,
                                          @RequestParam(required = false) Integer pageNum,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String status) {
        authService.assertAdminOrDispatcherOrKeeper(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody AgvTask entity) {
        authService.assertAdminOrDispatcher(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody AgvTask entity) {
        authService.assertAdminOrDispatcher(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/dispatch/{id}")
    public Result<Void> dispatch(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "DISPATCHED");
        return Result.success();
    }

    @PutMapping("/execute/{id}")
    public Result<Void> execute(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "RUNNING");
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    public Result<Void> complete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "COMPLETED");
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<Void> cancel(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "CANCELLED");
        return Result.success();
    }
}
