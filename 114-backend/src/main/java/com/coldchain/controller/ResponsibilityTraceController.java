package com.coldchain.controller;

import com.coldchain.common.Result;
import com.coldchain.entity.ResponsibilityTrace;
import com.coldchain.service.AuthService;
import com.coldchain.service.ResponsibilityTraceService;
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
@RequestMapping("/api/responsibility")
@RequiredArgsConstructor
public class ResponsibilityTraceController {
    private final AuthService authService;
    private final ResponsibilityTraceService service;

    @GetMapping("/page")
    public Result<PageInfo<ResponsibilityTrace>> page(@RequestAttribute String role,
                                                      @RequestParam(required = false) Integer pageNum,
                                                      @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) String status) {
        authService.assertAdminOrSupervisor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody ResponsibilityTrace entity) {
        authService.assertAdminOrSupervisor(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody ResponsibilityTrace entity) {
        authService.assertAdminOrSupervisor(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/review/{id}")
    public Result<Void> review(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSupervisor(role);
        service.updateStatus(id, "REVIEWED");
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSupervisor(role);
        service.updateStatus(id, "CONFIRMED");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSupervisor(role);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }
}
