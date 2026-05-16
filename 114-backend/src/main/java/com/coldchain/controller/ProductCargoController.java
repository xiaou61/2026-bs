package com.coldchain.controller;

import com.coldchain.common.Result;
import com.coldchain.entity.ProductCargo;
import com.coldchain.service.AuthService;
import com.coldchain.service.ProductCargoService;
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
@RequestMapping("/api/cargo")
@RequiredArgsConstructor
public class ProductCargoController {
    private final AuthService authService;
    private final ProductCargoService service;

    @GetMapping("/page")
    public Result<PageInfo<ProductCargo>> page(@RequestAttribute String role,
                                               @RequestParam(required = false) Integer pageNum,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) String keyword,
                                               @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody ProductCargo entity) {
        authService.assertAdminOrCarrier(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody ProductCargo entity) {
        authService.assertAdminOrCarrier(role);
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
        authService.assertAdminOrDispatcher(role);
        service.updateStatus(id, "SHIPPED");
        return Result.success();
    }

    @PutMapping("/arrive/{id}")
    public Result<Void> arrive(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCarrier(role);
        service.updateStatus(id, "ARRIVED");
        return Result.success();
    }
}
