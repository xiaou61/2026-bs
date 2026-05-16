package com.crossborder.controller;

import com.crossborder.common.Result;
import com.crossborder.entity.TaxFeeRecord;
import com.crossborder.service.AuthService;
import com.crossborder.service.TaxFeeRecordService;
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
@RequestMapping("/api/tax")
@RequiredArgsConstructor
public class TaxFeeRecordController {
    private final AuthService authService;
    private final TaxFeeRecordService service;

    @GetMapping("/page")
    public Result<IPage<TaxFeeRecord>> page(@RequestAttribute String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        authService.assertAdminOrCustomsOrFinance(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody TaxFeeRecord entity) {
        authService.assertAdminOrCustoms(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody TaxFeeRecord entity) {
        authService.assertAdminOrCustoms(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrFinance(role);
        service.updateStatus(id, "PAID");
        return Result.success();
    }

    @PutMapping("/overdue/{id}")
    public Result<Void> overdue(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrFinance(role);
        service.updateStatus(id, "OVERDUE");
        return Result.success();
    }
}
