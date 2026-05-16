package com.livecommerce.controller;

import com.livecommerce.common.Result;
import com.livecommerce.entity.SampleRequest;
import com.livecommerce.service.AuthService;
import com.livecommerce.service.SampleRequestService;
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
@RequestMapping("/api/sample")
@RequiredArgsConstructor
public class SampleRequestController {
    private final AuthService authService;
    private final SampleRequestService service;

    @GetMapping("/page")
    public Result<PageInfo<SampleRequest>> page(@RequestAttribute String role,
                                                @RequestParam(required = false) Integer pageNum,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) String status) {
        authService.assertAdminOrOperatorOrMerchant(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody SampleRequest entity) {
        authService.assertAdminOrOperatorOrMerchant(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody SampleRequest entity) {
        authService.assertAdminOrOperatorOrMerchant(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/send/{id}")
    public Result<Void> send(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMerchant(role);
        service.updateStatus(id, "SENT");
        return Result.success();
    }

    @PutMapping("/receive/{id}")
    public Result<Void> receive(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOperator(role);
        service.updateStatus(id, "RECEIVED");
        return Result.success();
    }
}
