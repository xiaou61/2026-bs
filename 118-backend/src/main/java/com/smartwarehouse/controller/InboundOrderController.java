package com.smartwarehouse.controller;

import com.github.pagehelper.PageInfo;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.InboundOrder;
import com.smartwarehouse.service.AuthService;
import com.smartwarehouse.service.InboundOrderService;
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
@RequestMapping("/api/inbound")
@RequiredArgsConstructor
public class InboundOrderController {
    private final AuthService authService;
    private final InboundOrderService service;

    @GetMapping("/page")
    public Result<PageInfo<InboundOrder>> page(@RequestAttribute String role,
                                               @RequestParam(required = false) Integer pageNum,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) String keyword,
                                               @RequestParam(required = false) String status) {
        authService.assertAdminOrDispatcherOrKeeper(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody InboundOrder entity) {
        authService.assertAdminOrKeeper(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody InboundOrder entity) {
        authService.assertAdminOrKeeper(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/assign/{id}")
    public Result<Void> assign(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "ASSIGNED");
        return Result.success();
    }

    @PutMapping("/receive/{id}")
    public Result<Void> receive(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "RECEIVED");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
