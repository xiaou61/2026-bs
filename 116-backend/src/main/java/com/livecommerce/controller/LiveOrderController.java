package com.livecommerce.controller;

import com.livecommerce.common.Result;
import com.livecommerce.entity.LiveOrder;
import com.livecommerce.service.AuthService;
import com.livecommerce.service.LiveOrderService;
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
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class LiveOrderController {
    private final AuthService authService;
    private final LiveOrderService service;

    @GetMapping("/page")
    public Result<PageInfo<LiveOrder>> page(@RequestAttribute String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        authService.assertAdminOrOperatorOrService(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody LiveOrder entity) {
        authService.assertAdminOrOperator(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody LiveOrder entity) {
        authService.assertAdminOrOperator(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/ship/{id}")
    public Result<Void> ship(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOperator(role);
        service.updateStatus(id, "SHIPPED");
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    public Result<Void> complete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrService(role);
        service.updateStatus(id, "COMPLETED");
        return Result.success();
    }
}
