package com.coldchain.controller;

import com.coldchain.common.Result;
import com.coldchain.entity.TransportOrder;
import com.coldchain.service.AuthService;
import com.coldchain.service.TransportOrderService;
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
public class TransportOrderController {
    private final AuthService authService;
    private final TransportOrderService service;

    @GetMapping("/page")
    public Result<PageInfo<TransportOrder>> page(@RequestAttribute String role,
                                                 @RequestParam(required = false) Integer pageNum,
                                                 @RequestParam(required = false) Integer pageSize,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody TransportOrder entity) {
        authService.assertAdminOrDispatcher(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody TransportOrder entity) {
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

    @PutMapping("/start/{id}")
    public Result<Void> start(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCarrier(role);
        service.updateStatus(id, "IN_TRANSIT");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCarrier(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
