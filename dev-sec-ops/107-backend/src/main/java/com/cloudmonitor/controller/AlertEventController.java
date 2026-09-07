package com.cloudmonitor.controller;

import com.cloudmonitor.common.Result;
import com.cloudmonitor.entity.AlertEvent;
import com.cloudmonitor.service.AuthService;
import com.cloudmonitor.service.AlertEventService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class AlertEventController {
    private final AlertEventService service;
    private final AuthService authService;

    @GetMapping("/page")
    public Result<IPage<AlertEvent>> page(@RequestAttribute String role,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody AlertEvent entity) {
        authService.assertAdminOrOpsOrSre(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody AlertEvent entity) {
        authService.assertAdminOrOpsOrSre(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSreOrManager(role);
        service.delete(id);
        return Result.success();
    }



    @PutMapping("/ack/{id}")
    public Result<Void> ack(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOpsOrSre(role);
        service.updateStatus(id, "ACKED");
        return Result.success();
    }


    @PutMapping("/resolve/{id}")
    public Result<Void> resolve(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOpsOrSre(role);
        service.updateStatus(id, "RESOLVED");
        return Result.success();
    }


    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSreOrManager(role);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }

}
