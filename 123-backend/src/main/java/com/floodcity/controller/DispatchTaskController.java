package com.floodcity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.floodcity.common.Result;
import com.floodcity.entity.DispatchTask;
import com.floodcity.service.AuthService;
import com.floodcity.service.DispatchTaskService;
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
public class DispatchTaskController {
    private final AuthService authService;
    private final DispatchTaskService service;

    @GetMapping("/page")
    public Result<IPage<DispatchTask>> page(@RequestAttribute String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        authService.assertAdminOrDispatcherOrManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody DispatchTask entity) {
        authService.assertAdminOrDispatcherOrManager(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody DispatchTask entity) {
        authService.assertAdminOrDispatcherOrManager(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/start/{id}")
    public Result<Void> start(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
