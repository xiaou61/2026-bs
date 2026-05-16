package com.coldchain.controller;

import com.coldchain.common.Result;
import com.coldchain.entity.ExceptionAlert;
import com.coldchain.service.AuthService;
import com.coldchain.service.ExceptionAlertService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/alert")
@RequiredArgsConstructor
public class ExceptionAlertController {
    private final AuthService authService;
    private final ExceptionAlertService service;

    @GetMapping("/page")
    public Result<PageInfo<ExceptionAlert>> page(@RequestAttribute String role,
                                                 @RequestParam(required = false) Integer pageNum,
                                                 @RequestParam(required = false) Integer pageSize,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody ExceptionAlert entity) {
        authService.assertAdminOrCarrier(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody ExceptionAlert entity) {
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

    @PutMapping("/ack/{id}")
    public Result<Void> ack(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "ACKED");
        return Result.success();
    }

    @PutMapping("/resolve/{id}")
    public Result<Void> resolve(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "RESOLVED");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSupervisor(role);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }
}
