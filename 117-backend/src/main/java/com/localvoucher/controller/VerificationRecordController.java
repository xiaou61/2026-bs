package com.localvoucher.controller;

import com.localvoucher.common.Result;
import com.localvoucher.entity.VerificationRecord;
import com.localvoucher.service.AuthService;
import com.localvoucher.service.VerificationRecordService;
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
@RequestMapping("/api/verification")
@RequiredArgsConstructor
public class VerificationRecordController {
    private final AuthService authService;
    private final VerificationRecordService service;

    @GetMapping("/page")
    public Result<IPage<VerificationRecord>> page(@RequestAttribute String role,
                                                  @RequestParam(required = false) Integer pageNum,
                                                  @RequestParam(required = false) Integer pageSize,
                                                  @RequestParam(required = false) String keyword,
                                                  @RequestParam(required = false) String status) {
        authService.assertAdminOrMerchantOrCashier(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody VerificationRecord entity) {
        authService.assertAdminOrCashier(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody VerificationRecord entity) {
        authService.assertAdminOrCashier(role);
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
        authService.assertAdminOrCashier(role);
        service.updateStatus(id, "CONFIRMED");
        return Result.success();
    }

    @PutMapping("/revoke/{id}")
    public Result<Void> revoke(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCashier(role);
        service.updateStatus(id, "REVOKED");
        return Result.success();
    }
}
