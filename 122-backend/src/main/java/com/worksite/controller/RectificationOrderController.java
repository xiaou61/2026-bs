package com.worksite.controller;

import com.github.pagehelper.PageInfo;
import com.worksite.common.Result;
import com.worksite.entity.RectificationOrder;
import com.worksite.service.AuthService;
import com.worksite.service.RectificationOrderService;
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
@RequestMapping("/api/rectify")
@RequiredArgsConstructor
public class RectificationOrderController {
    private final AuthService authService;
    private final RectificationOrderService service;

    @GetMapping("/page")
    public Result<PageInfo<RectificationOrder>> page(@RequestAttribute String role,
                                                     @RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String keyword,
                                                     @RequestParam(required = false) String status) {
        authService.assertAdminOrTeamLeaderOrSupervisor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RectificationOrder entity) {
        authService.assertAdminOrSupervisor(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RectificationOrder entity) {
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

    @PutMapping("/submit/{id}")
    public Result<Void> submit(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTeamLeader(role);
        service.updateStatus(id, "ASSIGNED");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTeamLeader(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
