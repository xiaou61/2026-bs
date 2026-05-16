package com.crossborder.controller;

import com.crossborder.common.Result;
import com.crossborder.entity.ClearanceDeclaration;
import com.crossborder.service.AuthService;
import com.crossborder.service.ClearanceDeclarationService;
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
@RequestMapping("/api/declaration")
@RequiredArgsConstructor
public class ClearanceDeclarationController {
    private final AuthService authService;
    private final ClearanceDeclarationService service;

    @GetMapping("/page")
    public Result<IPage<ClearanceDeclaration>> page(@RequestAttribute String role,
                                                    @RequestParam(required = false) Integer pageNum,
                                                    @RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) String status) {
        authService.assertAdminOrCustomsOrOperator(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody ClearanceDeclaration entity) {
        authService.assertAdminOrCustoms(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody ClearanceDeclaration entity) {
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

    @PutMapping("/submit/{id}")
    public Result<Void> submit(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCustoms(role);
        service.updateStatus(id, "SUBMITTED");
        return Result.success();
    }

    @PutMapping("/release/{id}")
    public Result<Void> release(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCustoms(role);
        service.updateStatus(id, "RELEASED");
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCustoms(role);
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }
}
