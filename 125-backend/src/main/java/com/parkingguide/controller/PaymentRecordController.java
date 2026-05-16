package com.parkingguide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.parkingguide.common.Result;
import com.parkingguide.entity.PaymentRecord;
import com.parkingguide.service.AuthService;
import com.parkingguide.service.PaymentRecordService;
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
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentRecordController {
    private final AuthService authService;
    private final PaymentRecordService service;

    @GetMapping("/page")
    public Result<IPage<PaymentRecord>> page(@RequestAttribute String role,
                                             @RequestParam(required = false) Integer pageNum,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String status) {
        authService.assertAdminOrOperatorOrAnalyst(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody PaymentRecord entity) {
        authService.assertAdminOrOperator(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody PaymentRecord entity) {
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

    @PutMapping("/refund/{id}")
    public Result<Void> refund(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOperator(role);
        service.updateStatus(id, "REFUNDED");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOperatorOrGuard(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
